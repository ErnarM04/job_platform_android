package com.example.startups.api

import com.example.startups.models.Access
import com.example.startups.models.Job
import com.example.startups.models.Profile
import com.example.startups.models.Resume
import com.example.startups.models.User
import okhttp3.RequestBody
import org.example.TokenResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(ApiService::class.java)

    interface ApiService {
        @Headers("Content-Type: application/json")
        @POST("api/token/")
        fun getToken(@Body requestBody: RequestBody): Call<TokenResponse>

        @Headers("Content-Type: application/json")
        @POST("api/users/register/jobseeker/")
        fun register(@Body requestBody: RequestBody): Call<User>

        @Headers("Content-Type: application/json")
        @POST("api/token/refresh/")
        fun refresh(@Body requestBody: RequestBody): Call<Access>

        @Headers("Content-Type: application/json")
        @GET("api/users/jobseeker/profile/")
        fun getProfile(@Header("Authorization") access: String ): Call<Profile>

        @Headers("Content-Type: application/json")
        @PATCH("api/users/jobseeker/profile/")
        fun editProfile(@Header("Authorization") access: String, @Body requestBody: RequestBody): Call<Profile>

        @GET("api/users/resumes/")
        fun getResume(@Header("Authorization") access: String): Call<List<Resume>>

        @GET("api/companies/vacancies/")
        fun getVacancies(@Header("Authorization") access: String): Call<List<Job>>
    }
}