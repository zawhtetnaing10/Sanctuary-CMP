package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.PROFILE_PICTURE_SIZE
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR
import com.zg.sanctuary.core.platform_specific.rememberCameraManager
import com.zg.sanctuary.core.presentation.components.dialogs.ChoiceDialog
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.camera
import sanctuary.composeapp.generated.resources.camera_or_gallery
import sanctuary.composeapp.generated.resources.camera_sanctuary
import sanctuary.composeapp.generated.resources.gallery
import sanctuary.composeapp.generated.resources.image_sanctuary
import sanctuary.composeapp.generated.resources.placeholder_profile_picture

@Composable
fun ProfilePicture(
    onImagePicked: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {

    // Picked Image
    var pickedImageBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    // Image Picker (Gallery)
    val scope = rememberCoroutineScope()
    val imagePickerLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { pickedImages ->
            val pickedImage: ByteArray? = pickedImages.firstOrNull()

            pickedImage?.let {
                onImagePicked(pickedImage)
                pickedImageBitmap = it.toImageBitmap()
            }
        }
    )

    // Camera Permission
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val cameraPermissionController: PermissionsController = remember(factory) { factory.createPermissionsController() }
    BindEffect(cameraPermissionController)

    // Camera Manager
    val cameraManager = rememberCameraManager(
        onResult = { capturedImage ->
            capturedImage?.let {
                it.toByteArray()?.let { byteArray ->
                    onImagePicked(byteArray)
                }
                pickedImageBitmap = it.toImageBitmap()
            }
        }
    )

    // Show camera or gallery dialog
    var showCameraOrGalleryDialog by remember { mutableStateOf(false) }

    // Render dialog
    if (showCameraOrGalleryDialog) {
        ChoiceDialog(
            onDismissRequest = {
                showCameraOrGalleryDialog = false
            },
            message = stringResource(Res.string.camera_or_gallery),
            leftButtonIcon = painterResource(Res.drawable.camera_sanctuary),
            leftButtonLabel = stringResource(Res.string.camera),
            onTapLeftButton = {
                showCameraOrGalleryDialog = false
                // Ask camera permission
                scope.launch {
                    askCameraPermissionRecursive(
                        cameraPermissionController,
                        onPermissionGranted = {
                            // Open camera
                            cameraManager.launch()
                        },
                        onPermissionPermanentlyDenied = {
                            // TODO: - Show rationale dialog with launch settings button.
                        })
                }
            },
            rightButtonIcon = painterResource(Res.drawable.image_sanctuary),
            rightButtonLabel = stringResource(Res.string.gallery),
            onTapRightButton = {
                showCameraOrGalleryDialog = false
                // Open Gallery
                imagePickerLauncher.launch()
            }
        )
    }

    // UI
    Box(
        modifier = modifier.size(PROFILE_PICTURE_SIZE)
    ) {

        // Image
        pickedImageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
                    .clip(CircleShape)
            )
        } ?: run {
            Image(
                painter = painterResource(Res.drawable.placeholder_profile_picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
                    .clip(CircleShape)
            )
        }

        // Add picture button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = -MARGIN_SMALL, x = -MARGIN_SMALL)
                .size(MARGIN_XLARGE)
                .background(TEXT_FIELD_BACKGROUND_COLOR, shape = CircleShape)
                .clickable {
                    // Show a dialog and ask the user whether to open camera or gallery.
                    showCameraOrGalleryDialog = true
                }
        ) {
            Image(
                painterResource(Res.drawable.camera_sanctuary),
                contentDescription = null,
                modifier = Modifier.size(MARGIN_MEDIUM_2)
                    .align(Alignment.Center)
            )
        }
    }
}

/**
 *
 */
suspend fun askCameraPermissionRecursive(
    cameraPermissionController: PermissionsController,
    onPermissionGranted: () -> Unit,
    onPermissionPermanentlyDenied: () -> Unit
) {
    try {
        cameraPermissionController.providePermission(Permission.CAMERA)
        // Permission Granted.
        onPermissionGranted.invoke()
    } catch (_: DeniedAlwaysException) {
        // Permanently Denied
        onPermissionPermanentlyDenied.invoke()
    } catch (_: DeniedException) {
        // Denied. Ask again.
        askCameraPermissionRecursive(cameraPermissionController, onPermissionGranted, onPermissionPermanentlyDenied)
    }
}