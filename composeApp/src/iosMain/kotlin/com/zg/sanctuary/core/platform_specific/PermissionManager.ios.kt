package com.zg.sanctuary.core.platform_specific

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PermissionManager actual constructor(val onPermissionStatusChanged : (PermissionStatus) -> Unit) {
    @Composable
    actual fun AskPermission(permission: PermissionType) {
        println("Ask permission called yay!!!!!!!!")
    }

    @Composable
    actual fun isPermissionGranted(permission: PermissionType) : Boolean {
        println("Is permission granted called yay!!!!!!")
        return false
    }

    @Composable
    actual fun LaunchSettings(){
        println("Launch settings called yay!!!!!!")
    }
}

@Composable
actual fun createPermissionManager(onPermissionStatusChanged : (PermissionStatus) -> Unit): PermissionManager {
    return remember { PermissionManager(onPermissionStatusChanged) }
}