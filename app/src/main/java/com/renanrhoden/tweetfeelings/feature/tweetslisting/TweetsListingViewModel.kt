package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.usecase.GetSentimentUseCase
import com.renanrhoden.tweetfeelings.usecase.GetTweetsUseCase
import com.renanrhoden.tweetfeelings.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TweetsListingViewModel(
    private val getTweetsUseCase: GetTweetsUseCase,
    private val ln: GetSentimentUseCase
) : ViewModel() {
    val tweets = MutableLiveData<MutableList<Tweet>>(mutableListOf())
    val feeling = SingleLiveEvent<Float>()
    val errorFetch = SingleLiveEvent<String>()
    private val disposable = CompositeDisposable()
    private val INITIAL_MAX_ID = 0L
    private var lastTweetId = INITIAL_MAX_ID

    fun fetchTweets() {
        val maxId = if (lastTweetId == INITIAL_MAX_ID) null else lastTweetId
        getTweetsUseCase.getTweets("renanrhoden", maxId)
            .subscribeBy(
                onSuccess = {
                    tweets.value?.addAll(it)
                    tweets.value = tweets.value
                    lastTweetId = it.last().id
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
