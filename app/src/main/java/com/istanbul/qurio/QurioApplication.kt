package com.istanbul.qurio

import android.app.Application
import com.istanbul.qurio.di.AppComponent
import com.istanbul.qurio.di.DaggerAppComponent

class QurioApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()
    }
}