package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.feature.tweetslisting.TweetsListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TweetsListingViewModel(get()) }
}