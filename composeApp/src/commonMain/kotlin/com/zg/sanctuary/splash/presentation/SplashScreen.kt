package com.zg.sanctuary.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zg.sanctuary.core.LOGO_HEIGHT
import com.zg.sanctuary.core.LOGO_WIDTH
import com.zg.sanctuary.core.MARGIN_XLARGE
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sanctuary_logo

@Composable
fun SplashScreen(){
    Box(modifier = Modifier.fillMaxSize().background(Color.White)){
        // Logo
        Image(
            painterResource(Res.drawable.sanctuary_logo),
            contentDescription = null,
            modifier = Modifier.width(LOGO_WIDTH).height(LOGO_HEIGHT)
                .padding(top = MARGIN_XLARGE)
                .align(Alignment.Center)
        )
    }
}