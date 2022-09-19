package com.ricardo.heroesapp.data.datasource

import com.ricardo.heroesapp.core.ApiClient
import com.ricardo.heroesapp.data.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

private const val PAGE_LIMIT = 30

class RemoteDataSource {

    private val ts = System.currentTimeMillis().toString()
    private val apikey = "YOUR API KEY HERE"
    private val hashInput =
        ts.plus("YOUR PRIVATE API KEY").plus(apikey)

    suspend fun getAllHeroes(total: Int): Response<ApiResponse> {
        return withContext(Dispatchers.IO) {
            val appApi = ApiClient.build()
            return@withContext appApi.getHeroes(PAGE_LIMIT, total, ts, apikey, hashInput.md5())
        }
    }

    suspend fun getHeroDetail(heroId: Int): Response<ApiResponse> {
        return withContext(Dispatchers.IO) {
            val appApi = ApiClient.build()
            return@withContext appApi.getHeroDetail(heroId, ts, apikey, hashInput.md5())
        }
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}
