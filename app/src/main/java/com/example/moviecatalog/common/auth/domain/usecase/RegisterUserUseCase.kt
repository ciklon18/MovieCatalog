package com.example.moviecatalog.common.auth.domain.usecase

import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.token.domain.model.TokenResponse
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository){
    suspend fun execute(user: UserRegisterModel): Result<TokenResponse>{
        return try{
            authRepository.register(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}