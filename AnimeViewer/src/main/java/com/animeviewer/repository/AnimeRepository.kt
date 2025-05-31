package com.animeviewer.repository

import com.animeviewer.api.AnimeApiService
import com.animeviewer.model.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val apiService: AnimeApiService
) {
    fun getAnimes(siteId: String): Flow<List<Anime>> = flow {
        val animes = when (siteId) {
            "anime-sama" -> apiService.getAnimeSamaAnimes()
            "voiranime" -> apiService.getVoirAnimeAnimes()
            "franime" -> apiService.getFranimeAnimes()
            else -> throw IllegalArgumentException("Invalid site ID: $siteId")
        }
        emit(animes)
    }

    fun getAnimeDetails(siteId: String, animeId: String): Flow<Anime> = flow {
        val anime = when (siteId) {
            "anime-sama" -> apiService.getAnimeSamaDetails(animeId)
            "voiranime" -> apiService.getVoirAnimeDetails(animeId)
            "franime" -> apiService.getFranimeDetails(animeId)
            else -> throw IllegalArgumentException("Invalid site ID: $siteId")
        }
        emit(anime)
    }

    fun searchAnimes(siteId: String, query: String): Flow<List<Anime>> = flow {
        val animes = when (siteId) {
            "anime-sama" -> apiService.searchAnimeSama(query)
            "voiranime" -> apiService.searchVoirAnime(query)
            "franime" -> apiService.searchFranime(query)
            else -> throw IllegalArgumentException("Invalid site ID: $siteId")
        }
        emit(animes)
    }
} 