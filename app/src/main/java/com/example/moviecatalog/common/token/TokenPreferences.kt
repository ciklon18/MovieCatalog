package com.example.moviecatalog.common.token

import androidx.datastore.preferences.core.stringPreferencesKey

internal object PreferencesKeys {
    val TOKEN_KEY = stringPreferencesKey("token_key")
}

internal object Constants {
    const val USER_PREFERENCES_NAME = "user_preferences"
}