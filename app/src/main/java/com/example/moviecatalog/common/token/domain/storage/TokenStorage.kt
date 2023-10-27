package com.example.moviecatalog.common.token.domain.storage

interface TokenStorage {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun clearToken()
}