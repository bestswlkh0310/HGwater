package com.example.hgwater

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL = "https://api.hangang.life/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val client = OkHttpClient.Builder()

    private val server = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client.build())
        .build()

    val api: API = server.create(API::class.java)
}