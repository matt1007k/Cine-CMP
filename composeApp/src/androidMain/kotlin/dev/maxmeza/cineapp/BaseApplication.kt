package dev.maxmeza.cineapp

import android.app.Application
import dev.maxmeza.cineapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin {
            androidContext(this@BaseApplication)
        }
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}