package com.example.moviecatalog.common.token.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviecatalog.common.token.Constants
import com.example.moviecatalog.common.token.domain.storage.TokenStorage
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenStorageImpl @Inject constructor(
    context: Context
) : TokenStorage {


    private var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        Constants.TOKEN_STORAGE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(Constants.TOKEN_KEY, token)
            .apply()
    }

    override suspend fun getToken(): String {
        return sharedPreferences.getString(Constants.TOKEN_KEY, "") ?: ""
    }

    override suspend fun deleteToken() {
        sharedPreferences.edit()
            .remove(Constants.TOKEN_KEY)
            .apply()
    }

}