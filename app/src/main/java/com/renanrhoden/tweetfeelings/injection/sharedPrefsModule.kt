package com.renanrhoden.tweetfeelings.injection

import android.content.Context
import org.koin.core.qualifier.named
import org.koin.dsl.module

val NATURAL_LANGUAGE = "NATURAL_LANGUAGE"

val sharedPrefsModule = module {
    single(named(NATURAL_LANGUAGE)) {
        val PREFS = "NATURAL_LANGUAGE"

        get<Context>().getSharedPreferences(
            PREFS, Context.MODE_PRIVATE
        )
    }
}