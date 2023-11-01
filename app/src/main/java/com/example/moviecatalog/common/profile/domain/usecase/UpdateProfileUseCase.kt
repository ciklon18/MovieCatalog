package com.example.moviecatalog.common.profile.domain.usecase

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(token: String, profile: Profile): Result<Unit> {
        val response = profileRepository.putProfileData(token, profile)

        return if (response.isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(Throwable(response.exceptionOrNull()))
        }
    }
}