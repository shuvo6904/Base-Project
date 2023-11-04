package com.example.baseproject.network

import com.example.baseproject.network.model.AppVersionResponse
import com.example.baseproject.utils.APP_VERSION
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    @GET(APP_VERSION)
    suspend fun getAppVersion(
        @Query("operatingSystemId") os: String = "10"
    ): AppVersionResponse
}