package com.example.moviecatalog.commons.network.repository

import com.example.moviecatalog.commons.network.models.TokenResponse
import com.example.moviecatalog.commons.network.models.UserLoginModel
import com.example.moviecatalog.commons.network.models.UserRegisterModel
import com.example.moviecatalog.commons.network.service.ApiService

class AuthRepositoryImpl(private val apiService: ApiService) :
    AuthRepository {


    override suspend fun login(user: UserLoginModel): Result<TokenResponse> {
        return try {
            val response = apiService.login(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: UserRegisterModel): Result<TokenResponse> {
        return try {
            val response = apiService.register(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

