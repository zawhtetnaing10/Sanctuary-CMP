package com.zg.sanctuary.core.utils

import io.ktor.http.ContentType

object ImageFormatDetector {
    fun detectImageContentType(bytes: ByteArray): ContentType? {
        // Sanity Check
        if (bytes.size < 8) {
            return null
        }

        // Check for PNG
        if (bytes[0] == 0x89.toByte() &&
            bytes[1] == 0x50.toByte() && // 'P'
            bytes[2] == 0x4E.toByte() && // 'N'
            bytes[3] == 0x47.toByte() && // 'G'
            bytes[4] == 0x0D.toByte() && // CR (Carriage Return)
            bytes[5] == 0x0A.toByte() && // LF (Line Feed)
            bytes[6] == 0x1A.toByte() && // Sub (Substitute)
            bytes[7] == 0x0A.toByte()    // LF (Line Feed)
        ) {
            return ContentType.Image.PNG
        }

        // Check for JPEG or jpg
        if (bytes[0] == 0xFF.toByte() && bytes[1] == 0xD8.toByte()) {
            return ContentType.Image.JPEG
        }

        // Check for GIF.
        // TODO: - Uncomment this code if gif is supported
//        if (bytes[0] == 0x47.toByte() && // 'G'
//            bytes[1] == 0x49.toByte() && // 'I'
//            bytes[2] == 0x46.toByte()    // 'F'
//        ) {
//            return ContentType.Image.GIF
//        }

        // Unknown format. Return null
        return null
    }

    fun getDefaultExtension(contentType: ContentType): String {
        return when (contentType) {
            ContentType.Image.PNG -> "png"
            ContentType.Image.JPEG -> "jpg"
            ContentType.Image.GIF -> "gif"
            else -> "bin"
        }
    }
}