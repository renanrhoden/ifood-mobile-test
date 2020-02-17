package com.renanrhoden.tweetfeelings.model

import com.google.gson.annotations.SerializedName

data class Sentiment(
    @SerializedName("magnitude") val magnitude: Double,
    @SerializedName("score") val score: Double
)