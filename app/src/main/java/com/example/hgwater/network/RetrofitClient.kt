package com.example.hgwater.network

import android.util.Log
import com.example.hgwater.network.service.API
import com.example.hgwater.util.CLIENT_ID
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val url = "https://api.qwer.pw/request/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private const val TAG: String = "로그"
    private val LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY
    private val client = OkHttpClient.Builder()
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        try {
            Log.d(TAG, JSONObject(message).toString(4))
        } catch (e: Exception) {
            Log.d(TAG, message)
        }
    }

    private var baseInterceptor: Interceptor = Interceptor { chain ->
        Log.d(TAG, "RetrofitClient - () called")

        val originalRequest = chain.request()
        val addedUrl = originalRequest.url.newBuilder().addQueryParameter("apikey", CLIENT_ID).build()
        val finalRequest = originalRequest.newBuilder()
            .url(addedUrl)
            .method(originalRequest.method, originalRequest.body)
            .build()
        chain.proceed(finalRequest)
    }
    private val server: Retrofit
    val api: API

    init {
        loggingInterceptor.setLevel(LOGGING_LEVEL)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(baseInterceptor)

        server = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()
        api = server.create(API::class.java)
    }
}