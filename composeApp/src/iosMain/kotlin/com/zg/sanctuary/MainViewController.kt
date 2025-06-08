package com.zg.sanctuary

import androidx.compose.ui.window.ComposeUIViewController
import com.zg.sanctuary.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }