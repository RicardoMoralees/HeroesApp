package com.ricardo.heroesapp.core

import com.ricardo.heroesapp.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IServiceApiClient {
    @GET("/v1/public/characters")
    suspend fun getHeroes(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Response<ApiResponse>

    @GET("/v1/public/characters/{heroId}")
    suspend fun getHeroDetail(
        @Path("heroId") id: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Response<ApiResponse>
}