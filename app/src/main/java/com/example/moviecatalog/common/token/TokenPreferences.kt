package com.example.moviecatalog.common.token

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object PreferencesKeys {
    val TOKEN_KEY = stringPreferencesKey("token_key")
    val PROFILE_ID_KEY = stringPreferencesKey("profile_id_key")
    val PROFILE_NICKNAME_KEY = stringPreferencesKey("profile_nickname_key")
    val PROFILE_EMAIL_KEY = stringPreferencesKey("profile_email_key")
    val PROFILE_AVATAR_LINK_KEY = stringPreferencesKey("profile_avatar_link_key")
    val PROFILE_NAME_KEY = stringPreferencesKey("profile_name_key")
    val PROFILE_BIRTH_DATE_KEY = stringPreferencesKey("profile_birth_date_key")
    val PROFILE_GENDER_KEY = intPreferencesKey("profile_gender_key")
}

internal object Constants {
    const val TOKEN_PREFERENCES_NAME = "token_preferences"
    const val PROFILE_PREFERENCES_NAME = "profile_preferences"
}