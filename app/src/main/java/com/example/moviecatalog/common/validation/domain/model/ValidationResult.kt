package com.example.moviecatalog.common.validation.domain.model

data class ValidationResult(
    val isError: Boolean,
    val errorMessage: Int? = null
)
