package com.renanrhoden.tweetfeelings.feature.tweetslisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetsListingFragmentBinding
import com.renanrhoden.tweetfeelings.util.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class TweetsListingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.tweets.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.updateItems(it)
            if (it.isEmpty()) {
                showEmpty()
            } else {
                hideEmpty()
            }
        })

        viewModel.errorFetch.observe(this, Observer {
            Toast.makeText(requireContext(), R.string.error_fetch, Toast.LENGTH_SHORT).show()
        })

        viewModel.feeling.observe(this, Observer {
            val directions =
                TweetsListingFragmentDirections.actionTweetsListingFragmentToFeelingFragment(it)
            findNavController().navigate(directions)
        })

        viewModel.loading.observe(this, Observer {
            binding.loading.visibility = if (it) {
                hideEmpty()
                VISIBLE
            } else {
                INVISIBLE
            }
        })
    }

    override fun onRefresh() {
        viewModel.refreshTweets()
    }

    private fun showEmpty() {
        binding.emptyLottie.apply {
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun hideEmpty() {
        binding.emptyLottie.apply {
            visibility = INVISIBLE
            cancelAnimation()
        }
    }
}
