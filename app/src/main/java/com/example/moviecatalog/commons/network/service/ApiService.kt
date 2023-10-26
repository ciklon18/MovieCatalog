package com.example.moviecatalog.commons.network.service

import com.example.moviecatalog.commons.network.models.UserLoginModel
import com.example.moviecatalog.commons.network.models.TokenResponse
import com.example.moviecatalog.commons.network.models.UserRegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: UserLoginModel): TokenResponse

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel): TokenResponse
}