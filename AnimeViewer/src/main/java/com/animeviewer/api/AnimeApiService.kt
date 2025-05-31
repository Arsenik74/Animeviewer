package com.animeviewer.api

import com.animeviewer.model.Anime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {
    @GET("anime-sama/animes")
    suspend fun getAnimeSamaAnimes(): List<Anime>

    @GET("voiranime/animes")
    suspend fun getVoirAnimeAnimes(): List<Anime>

    @GET("franime/animes")
    suspend fun getFranimeAnimes(): List<Anime>

    @GET("anime-sama/anime/{id}")
    suspend fun getAnimeSamaDetails(@Path("id") id: String): Anime

    @GET("voiranime/anime/{id}")
    suspend fun getVoirAnimeDetails(@Path("id") id: String): Anime

    @GET("franime/anime/{id}")
    suspend fun getFranimeDetails(@Path("id") id: String): Anime

    @GET("anime-sama/search")
    suspend fun searchAnimeSama(@Query("query") query: String): List<Anime>

    @GET("voiranime/search")
    suspend fun searchVoirAnime(@Query("query") query: String): List<Anime>

    @GET("franime/search")
    suspend fun searchFranime(@Query("query") query: String): List<Anime>
} 