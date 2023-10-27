package com.example.moviecatalog.common.token

import com.auth0.android.jwt.JWT
import java.util.Date
import javax.inject.Inject

class JwtTokenHelper @Inject constructor() {
    fun isExpired(token: String): Boolean {
        return try {
            val expirationTime = JWT(token).expiresAt

            expirationTime != null && expirationTime.before(Date())
        } catch (e: Exception) {
            true
        }
    }

}