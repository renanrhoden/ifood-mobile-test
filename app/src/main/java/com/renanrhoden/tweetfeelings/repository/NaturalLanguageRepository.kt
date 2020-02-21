package com.renanrhoden.tweetfeelings.repository

import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.language.v1.CloudNaturalLanguage
import com.google.api.services.language.v1.CloudNaturalLanguageScopes
import com.google.api.services.language.v1.model.AnalyzeSentimentRequest
import com.google.api.services.language.v1.model.Document
import com.google.api.services.language.v1.model.Sentiment
import com.renanrhoden.tweetfeelings.R
import io.reactivex.Single
import java.io.IOException

class NaturalLanguageRepository(
    private val prefs: SharedPreferences,
    private val resources: Resources
) {

    private val TAG = "NaturalLanguage"
    private val PREF_ACCESS_TOKEN = "access_token"

    var credential: GoogleCredential? = null

    fun getSentiment(tweet: String): Single<Sentiment> {
        val api = CloudNaturalLanguage.Builder(
            NetHttpTransport(),
            GsonFactory()
        ) {
            credential?.initialize(it)
        }.build()
        return getAnalyzeSentiment(tweet, api)

    }

    private fun getAnalyzeSentiment(tweet: String, api: CloudNaturalLanguage): Single<Sentiment> {

        return Single.create<Sentiment> {
            try {
                credential = GoogleCredential()
                    .setAccessToken(getToken())
                    .createScoped(CloudNaturalLanguageScopes.all())
                val request = api.documents().analyzeSentiment(
                    AnalyzeSentimentRequest()
                        .setDocument(
                            Document()
                                .setContent(tweet)
                                .setType("PLAIN_TEXT")
                        )

                ).execute()

                it.onSuccess(request.documentSentiment)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    /*
    Code found at

        https://github.com/GoogleCloudPlatform/android-docs-samples/tree/master/nl/Language
    */

    private fun getToken(): String? {
        val currentToken =
            prefs.getString(PREF_ACCESS_TOKEN, null)

        // Check if the current token is still valid for a while
        if (currentToken != null) {
            credential = GoogleCredential()
                .setAccessToken(currentToken)
                .createScoped(CloudNaturalLanguageScopes.all())
            val seconds = credential?.expiresInSeconds
            if (seconds != null && seconds > 3600) {
                return currentToken
            }
        }

        // ***** WARNING *****
        // In this sample, we load the credential from a JSON file stored in a raw resource folder
        // of this client app. You should never do this in your app. Instead, store the file in your
        // server and obtain an access token from there.
        // *******************

        try {
            credential = GoogleCredential.fromStream(resources.openRawResource(R.raw.credential))
                .createScoped(CloudNaturalLanguageScopes.all())

            credential?.refreshToken()
            val accessToken = credential?.accessToken
            prefs.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply()
            return accessToken
        } catch (e: IOException) {
            Log.e(TAG, "Failed to obtain access token.", e)
        }
        return null
    }
}
