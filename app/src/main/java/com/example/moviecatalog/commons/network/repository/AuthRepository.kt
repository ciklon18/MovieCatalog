package com.example.moviecatalog.commons.network.repository

import com.example.moviecatalog.commons.network.models.TokenResponse
import com.example.moviecatalog.commons.network.models.UserLoginModel
import com.example.moviecatalog.commons.network.models.UserRegisterModel

interface AuthRepository {
    suspend fun login(user: UserLoginModel): Result<TokenResponse>

    suspend fun register(user: UserRegisterModel): Result<TokenResponse>
}