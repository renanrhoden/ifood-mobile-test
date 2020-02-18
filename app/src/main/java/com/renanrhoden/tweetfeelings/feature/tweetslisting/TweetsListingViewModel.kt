package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.repository.TweetsRepository
import com.renanrhoden.tweetfeelings.usecase.GetSentimentUseCase
import com.renanrhoden.tweetfeelings.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TweetsListingViewModel(
    private val repository: TweetsRepository,
    private val ln: GetSentimentUseCase
) : ViewModel() {
    val tweets = MutableLiveData<List<Tweet>>(listOf())
    val feeling = SingleLiveEvent<Float>()
    val errorFetch = SingleLiveEvent<String>()
    private val disposable = CompositeDisposable()

    fun fetchTweets() {
        repository.getTweets()
            .subscribeBy(
                onSuccess = {
                    tweets.value = it
                },
                onError = {
                    errorFetch.value = it.message
                }
            ).addTo(disposable)
    }

    fun getSentiment(tweet: Tweet) {
        ln.getSentiment(tweet.text)
            .subscribeBy(
                onSuccess = {
                    feeling.value = it.score
                },
                onError = {
                    errorFetch.value = "${it.message}"
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
