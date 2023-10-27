package com.example.moviecatalog.commons.storage


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataStoreRepository @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(
        name = Constants.USER_PREFERENCES_NAME
    )

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String {
        return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.TOKEN_KEY)?.toString()
            ?: ""
    }


    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}

private object PreferencesKeys {
    val TOKEN_KEY = stringPreferencesKey("token_key")
}

private object Constants {
    const val USER_PREFERENCES_NAME = "user_preferences"
}