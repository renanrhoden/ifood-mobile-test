package com.renanrhoden.tweetfeelings

import android.app.Application
import com.renanrhoden.tweetfeelings.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    sharedPrefsModule,
                    resourcesModule
                )
            )
        }
    }
}