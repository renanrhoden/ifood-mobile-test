package com.renanrhoden.tweetfeelings.injection

import android.content.Context
import org.koin.dsl.module

val resourcesModule = module {
    factory { get<Context>().resources }
}