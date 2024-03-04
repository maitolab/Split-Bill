package com.solid.app

import android.app.Application
import com.solid.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import io.chipmango.base.CoreApplication
import timber.log.Timber

@HiltAndroidApp
class App : CoreApplication() {
    override fun isLogEnabled(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun onCreate() {
        super.onCreate()
    }
}