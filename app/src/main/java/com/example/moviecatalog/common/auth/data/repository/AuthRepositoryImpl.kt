package com.example.moviecatalog.common.auth.data.repository

import com.example.moviecatalog.common.token.domain.model.TokenResponse
import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.auth.data.service.AuthApiService
import com.example.moviecatalog.common.auth.domain.model.LogoutResponse

class AuthRepositoryImpl(private val authApiService: AuthApiService) : AuthRepository {


    override suspend fun login(user: UserLoginModel): Result<TokenResponse> {
        return try {
            val response = authApiService.login(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: UserRegisterModel): Result<TokenResponse> {
        return try {
            val response = authApiService.register(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() : Result<LogoutResponse>{
        return try {
            val response = authApiService.logout()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

