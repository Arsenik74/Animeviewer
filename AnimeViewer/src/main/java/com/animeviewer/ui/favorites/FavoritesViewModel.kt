package com.animeviewer.ui.favorites

import androidx.lifecycle.ViewModel
import com.animeviewer.data.local.FavoriteAnime
import com.animeviewer.data.local.FavoriteAnimeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteAnimeDao: FavoriteAnimeDao
) : ViewModel() {
    val favorites: Flow<List<FavoriteAnime>> = favoriteAnimeDao.getAllFavorites()
} 