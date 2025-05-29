package com.zg.sanctuary

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationScreen
import com.zg.sanctuary.core.presentation.components.dialogs.ChoiceDialog

@Preview(showBackground = true, backgroundColor = 0xFF000000,)
@Composable
fun ChoiceDialogPreview() {
    ChoiceDialog (
        onDismissRequest = {},
        leftButtonIcon = painterResource(R.drawable.ic_launcher_foreground),
        leftButtonLabel = "Camera",
        onTapLeftButton = {},
        rightButtonIcon = painterResource(R.drawable.ic_launcher_foreground),
        rightButtonLabel = "Gallery",
        onTapRightButton = {},
        message = "You can upload your profile picture using either your camera or your gallery. Which would you prefer?"
    )
}
