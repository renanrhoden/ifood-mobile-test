package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetsListingFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TweetsListingFragment : Fragment() {

    private val viewModel: TweetsListingViewModel by viewModel()
    private lateinit var binding : TweetsListingFragmentBinding

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

        return binding.root
    }

}
