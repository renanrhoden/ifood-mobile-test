package com.renanrhoden.tweetfeelings.feature.tweetslisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetsListingFragmentBinding
import com.renanrhoden.tweetfeelings.util.EndlessRecyclerViewScrollListener
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

        observeViewModel()

        binding.tweetsRecycler.addOnScrollListener(object : EndlessRecyclerViewScrollListener(
            binding.tweetsRecycler.layoutManager as LinearLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.fetchTweets()
            }
        })

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.tweets.observe(this, Observer {
            adapter.updateItems(it)
        })

        viewModel.errorFetch.observe(this, Observer {
            Toast.makeText(requireContext(), R.string.error_fetch, Toast.LENGTH_SHORT).show()
        })

        viewModel.feeling.observe(this, Observer {
            val directions =
                TweetsListingFragmentDirections.actionTweetsListingFragmentToFeelingFragment(it)
            findNavController().navigate(directions)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchTweets()
    }

}
