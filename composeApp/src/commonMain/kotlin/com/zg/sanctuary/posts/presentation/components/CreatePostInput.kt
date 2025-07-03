package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.zg.sanctuary.core.CREATE_POST_IMAGE_HEIGHT
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.core.platform_specific.rememberCameraManager
import com.zg.sanctuary.core.presentation.components.askCameraPermissionRecursive
import com.zg.sanctuary.core.presentation.components.dialogs.ChoiceDialog
import dev.icerock.moko.permissions.PermissionsController
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
import sanctuary.composeapp.generated.resources.create_post_label
import sanctuary.composeapp.generated.resources.gallery
import sanctuary.composeapp.generated.resources.image_sanctuary
import sanctuary.composeapp.generated.resources.placeholder_profile_picture

@Composable
fun CreatePostInput(
    content : String,
    onContentChanged : (String) -> Unit,
    onImagePicked : (ByteArray) -> Unit,
    onDeleteImage : () -> Unit
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

    // Render Dialog
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


    Column {
        // Input box and pick image trigger
        Surface(
            shape = RoundedCornerShape(MARGIN_MEDIUM_2),
            color = Color.White,
            border = BorderStroke(width = 1.dp, color = HINT_COLOR),
            modifier = Modifier.heightIn(min = 200.dp).fillMaxWidth().padding(MARGIN_MEDIUM_2)
        ) {
            Box {
                // Input Box
                TextField(
                    value = content,
                    onValueChange = {
                        onContentChanged(it)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(stringResource(Res.string.create_post_label), color = HINT_COLOR, fontSize = TEXT_REGULAR_2X)
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = MARGIN_XLARGE)
                )

                // Image
                Box(modifier = Modifier.height(MARGIN_XLARGE).fillMaxWidth().align(Alignment.BottomCenter)) {
                    Icon(
                        painterResource(Res.drawable.image_sanctuary),
                        contentDescription = null,
                        modifier = Modifier.padding(end = MARGIN_MEDIUM)
                            .size(MARGIN_LARGE)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                showCameraOrGalleryDialog = true
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MARGIN_MEDIUM_2))

        // Picked image
        pickedImageBitmap?.let {
            Box{
                // Picked Image
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(MARGIN_MEDIUM_2))
                        .padding(horizontal = MARGIN_MEDIUM_2)
                )

                // Delete image button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = MARGIN_MEDIUM_2, x = -MARGIN_LARGE)
                        .size(MARGIN_XLARGE)
                        .background(TEXT_FIELD_BACKGROUND_COLOR, shape = CircleShape)
                        .clickable {
                            pickedImageBitmap = null
                            onDeleteImage()
                        }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(MARGIN_MEDIUM_2)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }

}