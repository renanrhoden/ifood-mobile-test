package com.renanrhoden.tweetfeelings.service

import com.renanrhoden.tweetfeelings.model.Tweet
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterService {

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(
        @Query("screen_name") screen_name: String,
        @Query("max_id") maxId: Long?
    ): Single<List<Tweet>>
}