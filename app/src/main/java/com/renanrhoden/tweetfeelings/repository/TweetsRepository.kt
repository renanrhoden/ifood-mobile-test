package com.renanrhoden.tweetfeelings.repository

import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.service.TwitterService
import io.reactivex.Single

class TweetsRepository(private val service: TwitterService) {

    fun getTweets(): Single<List<Tweet>> {
        return Single.just(
            listOf(
                Tweet(" I've been really busy today"),
                Tweet("I'm not feeling well"),
                Tweet("this is not good")
            )
        )
    }
}