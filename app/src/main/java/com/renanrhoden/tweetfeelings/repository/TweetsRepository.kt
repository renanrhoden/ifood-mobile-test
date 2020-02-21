package com.renanrhoden.tweetfeelings.repository

import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.service.TwitterService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TweetsRepository(private val service: TwitterService) {

    fun getTweets(user: String, maxId: Long?): Single<List<Tweet>> {
        return service.getUserTimeline(user, maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}