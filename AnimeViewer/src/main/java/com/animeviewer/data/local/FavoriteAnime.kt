package com.animeviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_animes")
data class FavoriteAnime(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val siteId: String,
    val genres: List<String>,
    val rating: Float,
    val timestamp: Long = System.currentTimeMillis()
) 