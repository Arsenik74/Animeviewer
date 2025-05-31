package com.animeviewer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val number: Int,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String
) : Parcelable 