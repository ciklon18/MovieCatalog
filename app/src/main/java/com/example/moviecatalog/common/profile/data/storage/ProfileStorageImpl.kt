package com.example.moviecatalog.common.profile.data.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import com.example.moviecatalog.common.token.Constants
import com.example.moviecatalog.common.token.PreferencesKeys
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ProfileStorageImpl @Inject constructor(
    private val context: Context
) : ProfileStorage {
    private val Context.dataStore by preferencesDataStore(
        name = Constants.PROFILE_PREFERENCES_NAME
    )

    override suspend fun saveProfile(profile: Profile) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.PROFILE_ID_KEY] = profile.id
            prefs[PreferencesKeys.PROFILE_NICKNAME_KEY] = profile.nickName
            prefs[PreferencesKeys.PROFILE_EMAIL_KEY] = profile.email
            prefs[PreferencesKeys.PROFILE_AVATAR_LINK_KEY] = profile.avatarLink
            prefs[PreferencesKeys.PROFILE_NAME_KEY] = profile.name
            prefs[PreferencesKeys.PROFILE_BIRTH_DATE_KEY] = profile.birthDate
            prefs[PreferencesKeys.PROFILE_GENDER_KEY] = profile.gender
        }
    }

    override suspend fun getProfile(): Result<Profile> {
        val prefs = context.dataStore.data.firstOrNull()
        prefs?.let {
            return extractProfile(it)
                ?.let { profile ->
                    Result.success(profile)
                } ?: Result.failure(NoSuchElementException("Incomplete profile data"))
        }
        return Result.failure(NoSuchElementException("Profile data not found"))
    }

    private fun extractProfile(prefs: Preferences): Profile? {
        val id = prefs[PreferencesKeys.PROFILE_ID_KEY] ?: return null
        val nickName = prefs[PreferencesKeys.PROFILE_NICKNAME_KEY] ?: return null
        val email = prefs[PreferencesKeys.PROFILE_EMAIL_KEY] ?: return null
        val avatarLink = prefs[PreferencesKeys.PROFILE_AVATAR_LINK_KEY] ?: return null
        val name = prefs[PreferencesKeys.PROFILE_NAME_KEY] ?: return null
        val birthDate = prefs[PreferencesKeys.PROFILE_BIRTH_DATE_KEY] ?: return null
        val gender = prefs[PreferencesKeys.PROFILE_GENDER_KEY] ?: return null

        return Profile(
            id = id,
            nickName = nickName,
            email = email,
            avatarLink = avatarLink,
            name = name,
            birthDate = birthDate,
            gender = gender
        )
    }


    override suspend fun deleteProfile() {
        context.dataStore.edit { prefs ->
            prefs.remove(PreferencesKeys.PROFILE_ID_KEY)
            prefs.remove(PreferencesKeys.PROFILE_NICKNAME_KEY)
            prefs.remove(PreferencesKeys.PROFILE_EMAIL_KEY)
            prefs.remove(PreferencesKeys.PROFILE_AVATAR_LINK_KEY)
            prefs.remove(PreferencesKeys.PROFILE_NAME_KEY)
            prefs.remove(PreferencesKeys.PROFILE_BIRTH_DATE_KEY)
            prefs.remove(PreferencesKeys.PROFILE_GENDER_KEY)
        }
    }


}