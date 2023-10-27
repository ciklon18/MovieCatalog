package com.example.moviecatalog.common.validation.domain.usecase


class ValidateLoginUseCase{
    fun execute(value: String): Boolean {
        return value.isNotBlank()
    }
}