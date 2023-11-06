package com.example.moviecatalog.common.profile.domain.usecase

import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import javax.inject.Inject

class DeleteProfileFromLocalStorageUseCase @Inject constructor(
    private val profileStorage: ProfileStorage
) {
    suspend fun execute(){
        profileStorage.deleteProfile()
    }
}