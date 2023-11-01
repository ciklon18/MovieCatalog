package com.example.moviecatalog.profile.di

import com.example.moviecatalog.common.auth.domain.usecase.LogoutUserUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.UpdateProfileUseCase
import com.example.moviecatalog.common.token.domain.usecase.DeleteTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ProfileValidationUseCase
import com.example.moviecatalog.profile.presentation.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class ProfileScreenModule {
    @Provides
    fun provideProfileViewModel(
        getProfileUseCase: GetProfileUseCase,
        profileValidationUseCase: ProfileValidationUseCase,
        updateProfileUseCase: UpdateProfileUseCase,
        getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
        logoutUserUseCase: LogoutUserUseCase,
        deleteTokenFromLocalStorageUseCase: DeleteTokenFromLocalStorageUseCase
    ): ProfileViewModel {
        return ProfileViewModel(
            getProfileUseCase = getProfileUseCase,
            profileValidationUseCase = profileValidationUseCase,
            updateProfileUseCase = updateProfileUseCase,
            getTokenFromLocalStorageUseCase = getTokenFromLocalStorageUseCase,
            logoutUserUseCase = logoutUserUseCase,
            deleteTokenFromLocalStorageUseCase = deleteTokenFromLocalStorageUseCase
        )
    }
}