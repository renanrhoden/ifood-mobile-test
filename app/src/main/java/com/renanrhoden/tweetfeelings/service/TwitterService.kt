package com.renanrhoden.tweetfeelings.service

import com.renanrhoden.tweetfeelings.model.Tweet
import io.reactivex.Single
import retrofit2.http.GET

interface TwitterService {

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(): Single<List<Tweet>>
}