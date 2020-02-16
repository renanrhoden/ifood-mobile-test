package com.renanrhoden.tweetfeelings.model

import com.google.gson.annotations.SerializedName

data class Tweet(
    @SerializedName("text") val text: String
)