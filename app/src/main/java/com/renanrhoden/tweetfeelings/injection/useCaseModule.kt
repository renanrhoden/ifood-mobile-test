package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.usecase.GetSentimentUseCase
import com.renanrhoden.tweetfeelings.usecase.GetTweetsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetTweetsUseCase(get()) }
    single { GetSentimentUseCase(get()) }
}