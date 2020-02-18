package com.renanrhoden.tweetfeelings.usecase

import com.google.api.services.language.v1.model.Sentiment
import com.renanrhoden.tweetfeelings.repository.NaturalLanguageRepository
import io.reactivex.Single

class GetSentimentUseCase(private val repository: NaturalLanguageRepository) {
    fun getSentiment(tweet: String): Single<Sentiment> {

        return repository.getSentiment(tweet)
    }
}