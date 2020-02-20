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
    val user = MutableLiveData<String>()
    val feeling = SingleLiveEvent<Float>()
    val errorFetch = SingleLiveEvent<String>()
    val loading = SingleLiveEvent<Boolean>()
    private val disposable = CompositeDisposable()
    private val INITIAL_MAX_ID = 0L
    private var lastTweetId = INITIAL_MAX_ID

    fun fetchTweets() {
        loading.value = true
        user.value?.let {
            val maxId = if (lastTweetId == INITIAL_MAX_ID) null else lastTweetId - 1
            getTweetsUseCase.getTweets(it, maxId)
                .subscribeBy(
                    onSuccess = {
                        loading.value = false
                        tweets.value?.addAll(it)
                        tweets.value = tweets.value
                        lastTweetId = it.lastOrNull()?.id ?: INITIAL_MAX_ID
                    },
                    onError = {
                        loading.value = false
                        errorFetch.value = it.message
                    }
                ).addTo(disposable)
        }
    }

    fun getSentiment(tweet: Tweet) {
        loading.value = true
        ln.getSentiment(tweet.text)
            .subscribeBy(
                onSuccess = {
                    loading.value = false
                    feeling.value = it.score
                },
                onError = {
                    loading.value = false
                    errorFetch.value = "${it.message}"
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun refreshTweets() {
        lastTweetId = INITIAL_MAX_ID
        tweets.value?.clear()
        fetchTweets()
    }

    fun fetchTweetsFromUser() {
        lastTweetId = INITIAL_MAX_ID
        tweets.value?.clear()
        if (user.value?.isEmpty() != false) {
            loading.value = false
        } else {
            fetchTweets()
        }
    }
}
