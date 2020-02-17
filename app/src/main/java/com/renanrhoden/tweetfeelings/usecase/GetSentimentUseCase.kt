package com.renanrhoden.tweetfeelings.usecase

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.language.v1.CloudNaturalLanguage
import com.google.api.services.language.v1.model.AnalyzeSentimentRequest
import com.google.api.services.language.v1.model.AnalyzeSentimentResponse
import com.google.api.services.language.v1.model.Document
import com.google.api.services.language.v1.model.Sentiment
import com.renanrhoden.tweetfeelings.repository.NaturalLanguageRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetSentimentUseCase(private val repository: NaturalLanguageRepository) {

    var credential: GoogleCredential? = null

    fun getSentiment(): Single<Sentiment> {
        val api = CloudNaturalLanguage.Builder(
            NetHttpTransport(),
            GsonFactory()
        ) {
            credential?.initialize(it)
        }.build()

        return getAnalyzeSentiment(api)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAnalyzeSentiment(api: CloudNaturalLanguage): Single<Sentiment> {

        return Single.create<Sentiment> {
            try {
                val request = api.documents().analyzeSentiment(
                    AnalyzeSentimentRequest()
                        .setDocument(
                            Document()
                                .setContent("")
                                .setType("PLAIN_TEXT")
                        )

                ).execute()

                it.onSuccess(request.documentSentiment)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}