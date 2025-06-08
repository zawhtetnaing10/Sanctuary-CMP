package com.zg.sanctuary

import android.app.Application
import com.zg.sanctuary.di.initKoin
import org.koin.android.ext.koin.androidContext

class SanctuaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@SanctuaryApplication)
        }
    }
}