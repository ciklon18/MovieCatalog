package com.example.moviecatalog.common.profile.domain.usecase

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import javax.inject.Inject

class SetProfileToLocalStorageUseCase @Inject constructor(
    private val profileStorage: ProfileStorage
) {
    suspend fun execute(profile: Profile) {
        profileStorage.saveProfile(profile)
    }
}