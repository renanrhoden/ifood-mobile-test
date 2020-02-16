package com.renanrhoden.tweetfeelings.feature.tweetslisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetsListingFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TweetsListingFragment : Fragment() {

    private val viewModel: TweetsListingViewModel by viewModel()
    private val adapter: TweetsListingAdapter by lazy {
        TweetsListingAdapter(requireContext())
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

        viewModel.fetchTweets()

        return binding.root
    }

}
