package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.repository.NaturalLanguageRepository
import com.renanrhoden.tweetfeelings.repository.TweetsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { TweetsRepository(get()) }
    single {
        NaturalLanguageRepository(
            get(named(NATURAL_LANGUAGE)),
            get(named(CREDENTIAL_RESOURCE))
        )
    }
}