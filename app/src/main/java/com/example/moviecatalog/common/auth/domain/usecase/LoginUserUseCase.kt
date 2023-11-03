package com.example.moviecatalog.common.auth.domain.usecase

import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.token.domain.model.TokenResponse
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(user: UserLoginModel): Result<TokenResponse>{
        return try {
            authRepository.login(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}