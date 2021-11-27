package com.example.messageapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MessageApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}