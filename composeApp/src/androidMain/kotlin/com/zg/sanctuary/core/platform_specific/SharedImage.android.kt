package com.zg.sanctuary.core.platform_specific

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedImage(private val bitmap: Bitmap?) {
    actual fun toByteArray(): ByteArray? {
        return if (bitmap != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            @Suppress("MagicNumber") bitmap.compress(
                Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream
            )
            byteArrayOutputStream.toByteArray()
        } else {
            println("toByteArray null")
            null
        }
    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        } else {
            println("toImageBitmap null")
            null
        }
    }
}