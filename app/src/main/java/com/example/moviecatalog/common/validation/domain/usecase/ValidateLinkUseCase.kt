package com.example.moviecatalog.common.validation.domain.usecase

class ValidateLinkUseCase {
    private val linkRegex =
        Regex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\$")

    fun execute(link: String): Boolean {
        return link.isBlank() || link.matches(linkRegex)
    }
}