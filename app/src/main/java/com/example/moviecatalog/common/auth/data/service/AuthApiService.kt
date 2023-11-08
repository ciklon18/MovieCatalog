package com.example.moviecatalog.common.auth.data.service

import com.example.moviecatalog.common.auth.domain.model.LogoutResponse
import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.token.domain.model.TokenResponse
import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: UserLoginModel): TokenResponse

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel): TokenResponse

    @POST("api/account/logout")
    suspend fun logout() : LogoutResponse
}