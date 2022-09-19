package com.ricardo.heroesapp.data.model

data class ApiResponse(val data: ApiData)

data class ApiData(val results: List<Hero>)

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: HeroThumbnail,
    val comics: Comic,
    val series: Comic
)

data class HeroThumbnail(val path: String, val extension: String)
data class Comic(val available: Int, val items: List<Item>)
data class Item(val name: String)