package com.zg.sanctuary.core.platform_specific

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PermissionManager(onPermissionStatusChanged : (PermissionStatus) -> Unit) {
    @Composable
    fun AskPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun LaunchSettings()
}

@Composable
expect fun createPermissionManager(onPermissionStatusChanged : (PermissionStatus) -> Unit): PermissionManager