package com.example.hgwater.network.service

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("hangang_temp")
    fun getTemp(): Call<JsonElement>
}