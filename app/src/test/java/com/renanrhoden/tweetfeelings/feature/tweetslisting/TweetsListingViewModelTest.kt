package com.renanrhoden.tweetfeelings.feature.tweetslisting

import com.google.api.services.language.v1.model.Sentiment
import com.renanrhoden.tweetfeelings.SynchronousTestSchedulerRule
import com.renanrhoden.tweetfeelings.model.Tweet
import com.renanrhoden.tweetfeelings.usecase.GetSentimentUseCase
import com.renanrhoden.tweetfeelings.usecase.GetTweetsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TweetsListingViewModelTest {

    @get:Rule
    val rule = SynchronousTestSchedulerRule()

    private var getTweetsUseCase: GetTweetsUseCase = mockk()
    private var getSentimentUseCase: GetSentimentUseCase = mockk()
    private var viewModel = spyk(TweetsListingViewModel(getTweetsUseCase, getSentimentUseCase))

    @Before
    fun setup() {
        getTweetsUseCase = mockk()
        getSentimentUseCase = mockk()
        viewModel = TweetsListingViewModel(getTweetsUseCase, getSentimentUseCase)
    }

    @Test
    fun whenLoadTweetsFromEmptyUser_mustHideLoading() {
        viewModel.user.value = ""
        viewModel.fetchTweetsFromUser()

        assertThat(viewModel.loading.value).isFalse()
    }

    @Test
    fun whenFirstFetch_maxIdMustBeNull() {
        every { getTweetsUseCase.getTweets(any(), any()) } returns Single.just(emptyList())
        viewModel.user.value = "Mock"
        viewModel.fetchTweets()
        verify { getTweetsUseCase.getTweets("Mock", null) }
    }

    @Test
    fun whenFetchSuccess_mustHideLoading() {
        every { getTweetsUseCase.getTweets(any(), any()) } returns Single.just(emptyList())
        viewModel.user.value = "Mock"
        viewModel.fetchTweets()
        assertThat(viewModel.loading.value).isFalse()
    }

    @Test
    fun whenFetchFail_mustHideLoading() {
        every { getTweetsUseCase.getTweets(any(), any()) } returns Single.error(Exception())
        viewModel.user.value = "Mock"
        viewModel.fetchTweets()
        assertThat(viewModel.loading.value).isFalse()
    }

    @Test
    fun whenGetSentimentSuccess_mustHideLoading() {
        every { getSentimentUseCase.getSentiment(any()) } returns Single.just(Sentiment().setScore(0.5F))
        viewModel.user.value = "Mock"
        viewModel.getSentiment(Tweet("Text", 2819))
        assertThat(viewModel.loading.value).isFalse()
    }

    @Test
    fun whenGetSentimentFail_mustHideLoading() {
        every { getSentimentUseCase.getSentiment(any()) } returns Single.error(Exception())
        viewModel.user.value = "Mock"
        viewModel.getSentiment(Tweet("Text", 2819))
        assertThat(viewModel.loading.value).isFalse()
    }

    @Test
    fun whenUsernameNotEmpty_mustFetchTweets() {
        every { getTweetsUseCase.getTweets(any(), any()) } returns Single.just(listOf(Tweet("mock", 101)))
        viewModel.user.value = "hahaa"
        viewModel.fetchTweetsFromUser()
        assertThat(viewModel.tweets.value?.first()?.text).isEqualTo("mock")
        assertThat(viewModel.tweets.value?.first()?.id).isEqualTo(101)
    }
    @Test
    fun whenRefresh_mustFetchTweets() {
        every { getTweetsUseCase.getTweets(any(), any()) } returns Single.just(listOf(Tweet("mock", 101)))
        viewModel.user.value = "hahaa"
        viewModel.refreshTweets()
        assertThat(viewModel.tweets.value?.first()?.text).isEqualTo("mock")
        assertThat(viewModel.tweets.value?.first()?.id).isEqualTo(101)
    }
}