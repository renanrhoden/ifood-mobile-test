package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.repository.TweetsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TweetsRepository(get()) }
}