package com.example.moviecatalog.common.token.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviecatalog.common.token.Constants
import com.example.moviecatalog.common.token.PreferencesKeys
import com.example.moviecatalog.common.token.domain.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorageImpl @Inject constructor(
    private val context: Context
) : TokenStorage {

    private val Context.dataStore by preferencesDataStore(
        name = Constants.TOKEN_PREFERENCES_NAME
    )

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TOKEN_KEY] = token
        }
    }

    override suspend fun getToken(): String {
        return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.TOKEN_KEY)?.toString()
            ?: ""
    }

    override suspend fun deleteToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(PreferencesKeys.TOKEN_KEY)
        }
    }

}