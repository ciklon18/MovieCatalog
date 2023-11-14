package com.example.moviecatalog.common.profile.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import com.example.moviecatalog.common.token.Constants
import javax.inject.Inject

class ProfileStorageImpl @Inject constructor(
    context: Context
) : ProfileStorage {

    private var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        Constants.PROFILE_STORAGE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveProfile(profile: Profile) {
        sharedPreferences.edit().apply {
            putString(Constants.PROFILE_ID_KEY, profile.id)
            putString(Constants.PROFILE_NICKNAME_KEY, profile.nickName)
            putString(Constants.PROFILE_EMAIL_KEY, profile.email)
            putString(Constants.PROFILE_AVATAR_LINK_KEY, profile.avatarLink ?: "")
            putString(Constants.PROFILE_NAME_KEY, profile.name)
            putString(Constants.PROFILE_BIRTH_DATE_KEY, profile.birthDate)
            putInt(Constants.PROFILE_GENDER_KEY, profile.gender)
            apply()
        }
    }

    override suspend fun getProfile(): Result<Profile> {
        val id = sharedPreferences.getString(Constants.PROFILE_ID_KEY, null)
        val nickName = sharedPreferences.getString(Constants.PROFILE_NICKNAME_KEY, null)
        val email = sharedPreferences.getString(Constants.PROFILE_EMAIL_KEY, null)
        val avatarLink = sharedPreferences.getString(Constants.PROFILE_AVATAR_LINK_KEY, null)
        val name = sharedPreferences.getString(Constants.PROFILE_NAME_KEY, null)
        val birthDate = sharedPreferences.getString(Constants.PROFILE_BIRTH_DATE_KEY, null)
        val gender = sharedPreferences.getInt(Constants.PROFILE_GENDER_KEY, 0)

        if (id != null && nickName != null && email != null && name != null &&
            birthDate != null
        ) {
            return Result.success(
                Profile(
                    id = id,
                    nickName = nickName,
                    email = email,
                    avatarLink = avatarLink ?: "",
                    name = name,
                    birthDate = birthDate,
                    gender = gender
                )
            )
        } else {
            return Result.failure(NoSuchElementException("Incomplete profile data"))
        }
    }

    override suspend fun deleteProfile() {
        sharedPreferences.edit().apply {
            remove(Constants.PROFILE_ID_KEY)
            remove(Constants.PROFILE_NICKNAME_KEY)
            remove(Constants.PROFILE_EMAIL_KEY)
            remove(Constants.PROFILE_AVATAR_LINK_KEY)
            remove(Constants.PROFILE_NAME_KEY)
            remove(Constants.PROFILE_BIRTH_DATE_KEY)
            remove(Constants.PROFILE_GENDER_KEY)
            apply()
        }
    }


}