package com.example.moviecatalog.common.profile.domain.usecase

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import javax.inject.Inject

class GetProfileFromLocalStorageUseCase @Inject constructor(
    private val profileStorage: ProfileStorage
) {
    suspend fun execute(): Result<Profile>{
        return profileStorage.getProfile()
    }
}