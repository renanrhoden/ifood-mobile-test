package com.renanrhoden.tweetfeelings.feature.feeling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.FragmentFeelingBinding

class FeelingFragment : Fragment() {
    private lateinit var binding: FragmentFeelingBinding
    private val args: FeelingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().actionBar?.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feeling, container, false)

        when {
            args.score > 0.25 -> {
                binding.emoji.text = "😃"
                binding.rootLayout.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.background_color_happy,
                        null
                    )
                )
            }
            args.score > -0.25 -> {
                binding.emoji.text = "\uD83D\uDE10"
                binding.rootLayout.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.background_color_neutral,
                        null
                    )
                )
            }
            else -> {
                binding.emoji.text = "\uD83D\uDE14"
                binding.rootLayout.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.background_color_sad,
                        null
                    )
                )
            }
        }
        return binding.root
    }
}
