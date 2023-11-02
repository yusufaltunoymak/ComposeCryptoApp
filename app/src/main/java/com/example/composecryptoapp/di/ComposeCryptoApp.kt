package com.example.composecryptoapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeCryptoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}