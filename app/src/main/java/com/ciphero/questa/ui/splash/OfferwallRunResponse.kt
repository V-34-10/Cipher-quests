package com.ciphero.questa.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import com.ciphero.questa.R
import com.ciphero.questa.model.Offerwall
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OfferwallRunResponse(context: Context) {
    private var link: String = context.getString(R.string.link_response_offerwall)
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private suspend fun buildResponse(): Response? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(link)
                    .build()
                client.newCall(request).execute()
            } catch (e: TimeoutCancellationException) {
                Timber.tag("OfferwallRunResponse").e("Timeout getting headers: %s", e.message)
                null
            } catch (e: Exception) {
                Timber.tag("OfferwallRunResponse").e("Error making request: %s", e.message)
                null
            }
        }
    }

    @SuppressLint("TimberArgCount")
    suspend fun getOffers(): List<Offerwall> {
        val response = buildResponse()
        return if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            Timber.tag("OfferwallRunResponse").d("Response: %s", responseBody)
            parseParametersResponse(responseBody)
        } else {
            Timber.tag("OfferwallRunResponse")
                .e("%s%s", "%s - ", "Error getting game offers: %s", response?.code, response?.message)
            emptyList()
        }
    }

    private fun parseParametersResponse(json: String?): List<Offerwall> {
        return try {
            if (json.isNullOrEmpty()) {
                Timber.tag("OfferwallRunResponse").w("JSON is null or empty. Returning empty list.")
                emptyList()
            } else {
                Gson().fromJson(json, Array<Offerwall>::class.java).toList()
            }
        } catch (e: JsonSyntaxException) {
            Timber.tag("OfferwallRunResponse").e(e, "Error parsing JSON: %s", e.message)
            emptyList()
        }
    }
}