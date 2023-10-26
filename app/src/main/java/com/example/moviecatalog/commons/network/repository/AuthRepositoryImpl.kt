package com.example.moviecatalog.commons.network.repository

import com.example.moviecatalog.commons.network.models.LoginRequestBody
import com.example.moviecatalog.commons.network.models.TokenResponse
import com.example.moviecatalog.commons.network.service.ApiService

class AuthRepositoryImpl(private val apiService: ApiService) :
    AuthRepository {
    override suspend fun login(username: String, password: String): Result<TokenResponse> {
        return try {
            val request = LoginRequestBody(username = username, password = password)
            val response = apiService.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

