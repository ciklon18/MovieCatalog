package com.example.moviecatalog.common.profile.data.service

import com.example.moviecatalog.common.profile.domain.model.Profile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileApiService {
    @GET("api/account/profile")
    suspend fun getProfileData(@Header("Authorization") token: String): Response<Profile>

    @PUT("api/account/profile")
    suspend fun putProfileData(
        @Header("Authorization") token: String,
        @Body profile: Profile
    ): Response<Unit>

}

