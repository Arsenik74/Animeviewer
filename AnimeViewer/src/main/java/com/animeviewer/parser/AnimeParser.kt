package com.animeviewer.parser

import com.animeviewer.model.Anime
import com.animeviewer.model.Episode

interface AnimeParser {
    suspend fun parseAnimeList(html: String): List<Anime>
    suspend fun parseAnimeDetails(html: String): Anime
    suspend fun parseEpisodes(html: String): List<Episode>
    suspend fun parseVideoUrl(html: String): String
} 