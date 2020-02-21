package com.renanrhoden.tweetfeelings.injection

import com.renanrhoden.tweetfeelings.BuildConfig
import com.renanrhoden.tweetfeelings.auth.twitterConsumerKey
import com.renanrhoden.tweetfeelings.auth.twitterConsumerSecret
import com.renanrhoden.tweetfeelings.auth.twitterToken
import com.renanrhoden.tweetfeelings.auth.twitterTokenSecret
import com.renanrhoden.tweetfeelings.service.TwitterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

val networkModule = module {

    val TWITTER_SERVER = "TWITTER_SERVER"
    val GOOGLE_NL = "GOOGLE_NL"

    single(named(GOOGLE_NL)) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BuildConfig.LANGUAGE_GOOGLE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
    }

    single(named(TWITTER_SERVER)) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val auth = OkHttpOAuthConsumer(
            twitterConsumerKey,
            twitterConsumerSecret
        )
        auth.setTokenWithSecret(
            twitterToken,
            twitterTokenSecret
        )
        httpClient.addInterceptor(SigningInterceptor(auth))
        Retrofit.Builder()
            .baseUrl(BuildConfig.TWITTER_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
    }

    single {
        val retrofit: Retrofit = get(named(TWITTER_SERVER))
        retrofit.create(TwitterService::class.java)
    }
}