package com.renanrhoden.tweetfeelings.feature.tweetslisting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.renanrhoden.tweetfeelings.R
import com.renanrhoden.tweetfeelings.databinding.TweetItemBinding
import com.renanrhoden.tweetfeelings.feature.tweetslisting.TweetsListingAdapter.ViewHolder
import com.renanrhoden.tweetfeelings.model.Tweet

class TweetsListingAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var tweets = listOf<Tweet>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.tweet_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = tweets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    class ViewHolder(private val binding: TweetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tweet: Tweet) {
            binding.tweet = tweet
        }
    }
}