package com.example.moviecatalog.commons.network.repository

import com.example.moviecatalog.commons.network.models.TokenResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<TokenResponse>
}