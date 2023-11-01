package com.example.moviecatalog.common.auth.domain.repository

import com.example.moviecatalog.common.auth.domain.model.LogoutResponse
import com.example.moviecatalog.common.token.domain.model.TokenResponse
import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel

interface AuthRepository {
    suspend fun login(user: UserLoginModel): Result<TokenResponse>
    suspend fun register(user: UserRegisterModel): Result<TokenResponse>
    suspend fun logout(): Result<LogoutResponse>
}