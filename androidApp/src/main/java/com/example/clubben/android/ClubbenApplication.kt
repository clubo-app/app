package com.example.clubben.android

import android.app.Application
import co.touchlab.kermit.Logger
import com.example.clubben.android.di.appModule
import com.example.clubben.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ClubbenApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(enableNetworkLogs = true) {
            androidLogger()
            androidContext(this@ClubbenApplication)
            modules(appModule)
        }

        Logger.d { "ClubbenApplication" }
    }
}