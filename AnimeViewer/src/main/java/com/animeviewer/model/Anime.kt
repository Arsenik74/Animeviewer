package com.animeviewer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val episodes: List<Episode>,
    val genres: List<String>,
    val rating: Float,
    val siteId: String
) : Parcelable

data class Episode(
    val number: Int,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String
) 