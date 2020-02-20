package com.renanrhoden.tweetfeelings.usecase

import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.repository.TweetsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetTweetsUseCase(private val repository: TweetsRepository) {
    fun getTweets(user: String, maxId: Long?): Single<List<Tweet>> {
        return repository.getTweets(user, maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}