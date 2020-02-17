package com.renanrhoden.tweetfeelings.service

import com.renanrhoden.tweetfeelings.model.SentimentOutput
import io.reactivex.Single
import retrofit2.http.GET

interface NaturalLanguageService {

    @GET("")
    fun getSentiment(): Single<SentimentOutput>
}