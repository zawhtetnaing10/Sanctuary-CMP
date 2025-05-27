package com.zg.sanctuary.core

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.be_vietnam_pro_bold
import sanctuary.composeapp.generated.resources.be_vietnam_pro_medium
import sanctuary.composeapp.generated.resources.be_vietnam_pro_regular
import sanctuary.composeapp.generated.resources.be_vietnam_pro_semi_bold

@Composable
fun BeVietnamProFontFamily() =  FontFamily(
    Font(Res.font.be_vietnam_pro_regular, FontWeight.Normal),
    Font(Res.font.be_vietnam_pro_semi_bold, FontWeight.SemiBold),
    Font(Res.font.be_vietnam_pro_bold, FontWeight.Bold),
    Font(Res.font.be_vietnam_pro_medium, FontWeight.Medium)
)

@Composable
fun BeVietnamProTypography() = Typography().run {
    val fontFamily = BeVietnamProFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}

