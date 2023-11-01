package com.example.moviecatalog.common.profile.data.repository

import com.example.moviecatalog.common.profile.data.service.ProfileApiService
import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val profileApiService: ProfileApiService) : ProfileRepository {
    override suspend fun getProfileData(token: String): Result<Profile?> {
        return try {
            val response = profileApiService.getProfileData("Bearer $token")
            if (response.isSuccessful) {
                val profile = response.body()
                Result.success(profile)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putProfileData(token: String, profile: Profile): Result<Unit> {
        return try {
            val response = profileApiService.putProfileData("Bearer $token", profile)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Response was not successful, response code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}