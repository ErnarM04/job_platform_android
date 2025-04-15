package com.example.startups.api

import com.example.startups.models.DeepSeekRequest
import com.example.startups.models.DeepSeekResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

object AIClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deepseek.com/") // DeepSeek API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(AIService::class.java)

    interface AIService {
        @POST("v1/chat/completions")
        fun askDeepSeek(
            @Body request: DeepSeekRequest,
            @Header("Authorization") token: String
        ): Call<DeepSeekResponse>
    }
}