package com.example.moviecatalog.common.auth.domain.usecase

import com.example.moviecatalog.common.auth.domain.model.LogoutResponse
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute() : Result<LogoutResponse>{
        return authRepository.logout()
    }
}