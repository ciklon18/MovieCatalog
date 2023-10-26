package com.example.moviecatalog.commons.network.service

import com.example.moviecatalog.commons.network.models.LoginRequestBody
import com.example.moviecatalog.commons.network.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: LoginRequestBody): TokenResponse

}