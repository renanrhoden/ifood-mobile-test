package com.renanrhoden.tweetfeelings.feature.tweetslisting

import androidx.recyclerview.widget.DiffUtil
import com.renanrhoden.tweetfeelings.model.Tweet

class TweetsDiffUtilCallback(
    private val oldItems: List<Tweet>,
    private val newItems: List<Tweet>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}