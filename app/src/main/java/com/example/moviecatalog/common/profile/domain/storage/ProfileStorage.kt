package com.example.moviecatalog.common.profile.domain.storage

import com.example.moviecatalog.common.profile.domain.model.Profile

interface ProfileStorage {
    suspend fun saveProfile(profile: Profile)
    suspend fun getProfile(): Result<Profile>
    suspend fun deleteProfile()
}