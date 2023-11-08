package com.example.moviecatalog.common.token.domain.usecase

import com.example.moviecatalog.common.token.entity.JwtTokenHelper
import javax.inject.Inject

class IsTokenExpiredUseCase @Inject constructor(
    private val jwtTokenHelper: JwtTokenHelper
) {
    fun execute(token: String): Boolean{
        return jwtTokenHelper.isExpired(token)
    }
}