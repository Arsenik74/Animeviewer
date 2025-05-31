package com.animeviewer.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeviewer.data.local.FavoriteAnime
import com.animeviewer.data.local.FavoriteAnimeDao
import com.animeviewer.model.Anime
import com.animeviewer.model.Episode
import com.animeviewer.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val repository: AnimeRepository,
    private val favoriteAnimeDao: FavoriteAnimeDao
) : ViewModel() {

    private val _anime = MutableStateFlow<Anime?>(null)
    val anime: StateFlow<Anime?> = _anime.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadAnimeDetails(animeId: String, siteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val animeDetails = repository.getAnimeDetails(animeId, siteId)
                _anime.value = animeDetails
                checkIfFavorite(animeId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun checkIfFavorite(animeId: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteAnimeDao.isFavorite(animeId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentAnime = _anime.value ?: return@launch
            if (_isFavorite.value) {
                favoriteAnimeDao.deleteFavoriteById(currentAnime.id)
            } else {
                val favoriteAnime = FavoriteAnime(
                    id = currentAnime.id,
                    title = currentAnime.title,
                    description = currentAnime.description,
                    imageUrl = currentAnime.imageUrl,
                    siteId = currentAnime.siteId,
                    genres = currentAnime.genres,
                    rating = currentAnime.rating
                )
                favoriteAnimeDao.insertFavorite(favoriteAnime)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
} 