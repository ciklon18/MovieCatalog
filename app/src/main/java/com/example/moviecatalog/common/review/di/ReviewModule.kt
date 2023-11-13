package com.example.moviecatalog.common.review.di

import com.example.moviecatalog.common.review.data.reporitory.ReviewRepositoryImpl
import com.example.moviecatalog.common.review.data.service.ReviewApiService
import com.example.moviecatalog.common.review.domain.repository.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ReviewModule {
    @Provides
    fun provideApiImplementation(retrofit: Retrofit): ReviewApiService {
        return retrofit.create(ReviewApiService::class.java)
    }

    @Provides
    fun provideReviewRepository(reviewApiService: ReviewApiService): ReviewRepository {
        return ReviewRepositoryImpl(reviewApiService)
    }
}