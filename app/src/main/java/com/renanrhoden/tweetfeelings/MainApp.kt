package com.renanrhoden.tweetfeelings

import android.app.Application
import com.renanrhoden.tweetfeelings.injection.networkModule
import com.renanrhoden.tweetfeelings.injection.repositoryModule
import com.renanrhoden.tweetfeelings.injection.useCaseModule
import com.renanrhoden.tweetfeelings.injection.viewModelModule
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
            modules(listOf(viewModelModule, networkModule, repositoryModule, useCaseModule))
        }
    }
}