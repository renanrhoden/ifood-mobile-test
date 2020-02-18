package com.renanrhoden.tweetfeelings.feature.tweetslisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.language.v1.CloudNaturalLanguageScopes
import com.google.api.services.language.v1.CloudNaturalLanguageScopes.*
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetsListingFragmentBinding
import com.renanrhoden.tweetfeelings.model.Feeling
import com.renanrhoden.tweetfeelings.model.Feeling.*
import com.renanrhoden.tweetfeelings.usecase.GetSentimentUseCase
import com.renanrhoden.tweetfeelings.util.AccessTokenLoader
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TweetsListingFragment : Fragment() {

    private val viewModel: TweetsListingViewModel by viewModel()
    private val adapter: TweetsListingAdapter by lazy {
        TweetsListingAdapter(requireContext(), viewModel::getSentiment)
    }
    private lateinit var binding: TweetsListingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.tweets_listing_fragment,
            container,
            false
        )

        binding.tweetsRecycler.adapter = adapter
        binding.tweetsRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.tweets.observe(this, Observer {
            adapter.tweets = it
        })

        viewModel.errorFetch.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            Toast.makeText(requireContext(), R.string.error_fetch, Toast.LENGTH_SHORT).show()
        })

        viewModel.feeling.observe(this, Observer {
            when (it) {
                HAPPY -> {
                }
                NEUTRAL -> {
                }
                SAD -> {
                }
            }
        })

        viewModel.fetchTweets()

        return binding.root
    }

}
