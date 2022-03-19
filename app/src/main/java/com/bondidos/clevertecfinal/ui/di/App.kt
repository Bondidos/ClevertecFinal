package com.bondidos.clevertecfinal.ui.di

import android.app.Application
import android.content.Context

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerAppComponent.create()
            appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }