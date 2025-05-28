package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.PROFILE_PICTURE_SIZE
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR
import com.zg.sanctuary.core.platform_specific.PermissionManager
import com.zg.sanctuary.core.platform_specific.PermissionType
import com.zg.sanctuary.core.platform_specific.createPermissionManager
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.camera_sanctuary
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun ProfilePicture(
    // TODO: - Send the picked profile picture back to parent.
    modifier: Modifier = Modifier
) {

    // Permission Manager
    val permissionManager = createPermissionManager { permissionStatus ->
        // TODO:- Handle permission changes
    }

    // Launch Gallery State
    var launchGallery by remember { mutableStateOf(false) }
    var launchCamera by remember { mutableStateOf(false) }

    // If launch gallery changes, ask permission and launch gallery
    if(launchGallery) {
        // TODO: - Check permission and ask for it when required
        // Ask permission
        permissionManager.AskPermission(PermissionType.GALLERY)
        // Reset launch gallery
        launchGallery = false
    }

    if(launchCamera) {
        // TODO: - Check permission and ask for it when required
        // Ask permission
        permissionManager.AskPermission(PermissionType.CAMERA)
        // Reset launch camera
        launchCamera = false
    }

    Box(
        modifier = modifier.size(PROFILE_PICTURE_SIZE)
    ) {
        // Profile image
        Image(
            painterResource(Res.drawable.sample_profile_picture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
                .clip(CircleShape)
        )

        // Add picture button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = -MARGIN_SMALL, x = -MARGIN_SMALL)
                .size(MARGIN_XLARGE)
                .background(TEXT_FIELD_BACKGROUND_COLOR, shape = CircleShape)
                .clickable {
                    // TODO: - Show a dialog and ask the user whether to open camera or gallery.
                    // Open Gallery
                    launchGallery = true
                }
        ) {
            Image(
                painterResource(Res.drawable.camera_sanctuary),
                contentDescription = null,
                modifier = Modifier.size(MARGIN_MEDIUM_2)
                    .align(Alignment.Center)
            )
        }

        // Delete picture button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = MARGIN_SMALL, x = -MARGIN_SMALL)
                .size(MARGIN_XLARGE)
                .background(TEXT_FIELD_BACKGROUND_COLOR, shape = CircleShape)
                .clickable {

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