package com.renanrhoden.tweetfeelings.repository

import com.renanrhoden.tweetfeelings.model.Tweet
import io.reactivex.Single

class TweetsRepository {

    fun getTweets() : Single<List<Tweet>> {
        return Single.just(listOf(
            Tweet(" I've been really busy today"),
            Tweet("I'm not feeling well"),
            Tweet("this is not good")
        ))
    }
}