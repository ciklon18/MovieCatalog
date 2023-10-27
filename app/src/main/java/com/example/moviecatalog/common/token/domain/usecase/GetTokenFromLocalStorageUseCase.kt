package com.example.moviecatalog.common.token.domain.usecase

import com.example.moviecatalog.common.token.domain.storage.TokenStorage
import javax.inject.Inject

class GetTokenFromLocalStorageUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    suspend fun execute() : String {
        return tokenStorage.getToken()
    }
}