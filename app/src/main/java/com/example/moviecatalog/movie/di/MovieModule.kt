package com.example.moviecatalog.movie.di

import com.example.moviecatalog.common.favorite.domain.usecase.DeleteFavoriteUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.PostFavoriteUseCase
import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.review.domain.usecase.DeleteUserReviewUseCase
import com.example.moviecatalog.common.review.domain.usecase.EditUserReviewUseCase
import com.example.moviecatalog.common.review.domain.usecase.PostUserReviewUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.movie.presentation.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MovieModule {
    @Provides
    fun provideMovieViewModel(
        getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
        getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
        getMovieDetailsUseCase: GetMovieDetailsUseCase,
        getFavoritesUseCase: GetFavoritesUseCase,
        postFavoriteUseCase: PostFavoriteUseCase,
        deleteFavoriteUseCase: DeleteFavoriteUseCase,
        postUserReviewUseCase: PostUserReviewUseCase,
        editUserReviewUseCase: EditUserReviewUseCase,
        deleteUserReviewUseCase: DeleteUserReviewUseCase
    ): MovieViewModel {
        return MovieViewModel(
            getProfileFromLocalStorageUseCase = getProfileFromLocalStorageUseCase,
            getTokenFromLocalStorageUseCase = getTokenFromLocalStorageUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            postFavoriteUseCase = postFavoriteUseCase,
            deleteFavoriteUseCase = deleteFavoriteUseCase,
            postUserReviewUseCase = postUserReviewUseCase,
            editUserReviewUseCase = editUserReviewUseCase,
            deleteUserReviewUseCase = deleteUserReviewUseCase
        )
    }
}