package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.service.TwitterService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    val oauth_consumer_key = "dKos3bjIlMMzirWCxKORebZcA"
    val oauth_nonce = ""
    val oauth_signature = " "

    single { Retrofit.Builder()
        .baseUrl("http://google.com").build().create(TwitterService::class.java) }
}