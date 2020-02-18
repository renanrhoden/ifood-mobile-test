package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.tweetfeelings.model.Feeling
import com.renanrhoden.tweetfeelings.model.Feeling.*
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
    val feeling = MutableLiveData<Feeling>()
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
                    when {
                        it.score > 0.25 -> {
                            feeling.value = HAPPY
                        }
                        it.score > -0.75 -> {
                            feeling.value = NEUTRAL
                        }
                        else -> {
                            feeling.value = SAD
                        }
                    }
                },
                onError = {
                    errorFetch.value = "${it.message}"
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
