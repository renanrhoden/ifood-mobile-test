package com.renanrhoden.tweetfeelings.injection

import android.content.Context
import com.renanrhoden.tweetfeelings.R
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CREDENTIAL_RESOURCE = "CREDENTIAL_RESOURCE"

val resourcesModule = module {
    factory(named(CREDENTIAL_RESOURCE)) { get<Context>().resources.openRawResource(R.raw.credential) }
}