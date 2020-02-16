package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.usecase.GetTweetsUSeCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetTweetsUSeCase(get()) }
}