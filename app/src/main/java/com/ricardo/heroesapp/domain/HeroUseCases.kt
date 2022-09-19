package com.ricardo.heroesapp.domain

import com.ricardo.heroesapp.data.model.ApiResponse
import com.ricardo.heroesapp.data.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class HeroUseCases {

    private val repository = HeroRepository()

    suspend fun getAllHeroes(total: Int): Flow<Response<ApiResponse>> =
        repository.getAllHeroes(total)

    suspend fun getHeroDetail(heroId: Int): Flow<Response<ApiResponse>> =
        repository.getHeroDetail(heroId)

}