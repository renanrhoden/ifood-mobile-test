package com.renanrhoden.tweetfeelings.model

import com.google.gson.annotations.SerializedName

data class SentimentOutput(
    @SerializedName("documentSentiment") val sentiment: Sentiment
)