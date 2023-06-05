package com.example.englishwordxml2.app

import android.app.Application
import com.example.englishwordxml2.db.MyDbManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var myDbManager: MyDbManager

    override fun onCreate() {
        super.onCreate()
        myDbManager.openDb()
    }
}