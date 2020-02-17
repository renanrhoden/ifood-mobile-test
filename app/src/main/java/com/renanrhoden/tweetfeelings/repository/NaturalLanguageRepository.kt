package com.renanrhoden.tweetfeelings.repository

import com.renanrhoden.tweetfeelings.model.Sentiment
import com.renanrhoden.tweetfeelings.model.SentimentOutput
import com.renanrhoden.tweetfeelings.service.NaturalLanguageService
import io.reactivex.Single

class NaturalLanguageRepository(private val service: NaturalLanguageService) {
        fun getSentiment() : Single<Sentiment> {
            return service.getSentiment()
                .map {
                    it.sentiment
                }
        }
}
