package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.repository.TweetsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TweetsListingViewModel(private val repository: TweetsRepository) : ViewModel() {
    val tweets = MutableLiveData<List<Tweet>>(listOf())
    private val disposable = CompositeDisposable()

    fun fetchTweets() {
        repository.getTweets()
            .subscribeBy(
                onSuccess = {
                    tweets.value = it
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
