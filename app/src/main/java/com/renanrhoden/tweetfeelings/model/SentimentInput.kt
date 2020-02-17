package com.renanrhoden.tweetfeelings.model

import com.google.gson.annotations.SerializedName

class SentimentInput(
    @SerializedName("content")
    val content: String,
    @SerializedName("type")
    val type: String = "PLAIN_TEXT"
)