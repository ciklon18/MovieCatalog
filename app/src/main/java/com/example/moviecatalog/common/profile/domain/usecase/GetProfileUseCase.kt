package com.example.moviecatalog.common.profile.domain.usecase

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(token: String) : Result<Profile?> {
        return try{
            val result = profileRepository.getProfileData(token)

            if (result.isSuccess){
                Result.success(result.getOrNull())
            } else {
                Result.failure(result.exceptionOrNull() ?: Throwable("Empty data"))
            }
        } catch (e: Exception){
            Result.failure(Throwable(e.toString()))
        }

    }
}