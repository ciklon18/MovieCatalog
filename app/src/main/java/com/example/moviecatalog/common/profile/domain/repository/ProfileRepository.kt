package com.example.moviecatalog.common.profile.domain.repository

import com.example.moviecatalog.common.profile.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfileData(token: String): Result<Profile?>
    suspend fun putProfileData(token: String, profile: Profile): Result<Unit>
}