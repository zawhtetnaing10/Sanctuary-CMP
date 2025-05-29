package com.zg.sanctuary.core.platform_specific

import androidx.compose.ui.graphics.ImageBitmap

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedImage {
    fun toByteArray() : ByteArray?
    fun toImageBitmap() : ImageBitmap?
}