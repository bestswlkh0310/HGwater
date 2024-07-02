package com.example.hgwater

import retrofit2.http.GET

interface API {
    @GET("/")
    suspend fun getTemp(): Response
}