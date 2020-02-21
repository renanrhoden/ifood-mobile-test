package com.renanrhoden.tweetfeelings.usecase

import com.google.api.services.language.v1.model.Sentiment
import com.renanrhoden.tweetfeelings.repository.NaturalLanguageRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetSentimentUseCase(private val repository: NaturalLanguageRepository) {
    fun getSentiment(tweet: String): Single<Sentiment> {

        return repository.getSentiment(tweet)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}