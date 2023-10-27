package com.example.moviecatalog.common.token.domain.usecase

import com.example.moviecatalog.common.token.domain.storage.TokenStorage
import javax.inject.Inject

class SetTokenToLocalStorageUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    suspend fun execute(token: String){
        tokenStorage.saveToken(token)
    }
}