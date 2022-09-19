package com.ricardo.heroesapp.data.repository

import com.ricardo.heroesapp.data.datasource.RemoteDataSource
import com.ricardo.heroesapp.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HeroRepository {

    private val api = RemoteDataSource()

    suspend fun getAllHeroes(total: Int): Flow<Response<ApiResponse>> {
        val response = api.getAllHeroes(total)
        return flow {
            emit(response)
        }
    }

    suspend fun getHeroDetail(heroId: Int): Flow<Response<ApiResponse>> {
        val response = api.getHeroDetail(heroId)
        return flow {
            emit(response)
        }
    }

}