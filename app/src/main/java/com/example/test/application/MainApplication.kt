package com.example.test.application

import android.app.Application
import com.example.test.PreferenceUtil


class MainApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
//        prefs = PreferenceUtil(applicationContext)
    }
}