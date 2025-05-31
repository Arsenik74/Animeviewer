package com.animeviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeviewer.model.Anime
import com.animeviewer.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun loadAnimes(siteId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAnimes(siteId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Une erreur est survenue")
                }
                .collect { animes ->
                    _uiState.value = UiState.Success(animes)
                }
        }
    }

    fun searchAnimes(siteId: String, query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.searchAnimes(siteId, query)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Une erreur est survenue")
                }
                .collect { animes ->
                    _uiState.value = UiState.Success(animes)
                }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val animes: List<Anime>) : UiState()
        data class Error(val message: String) : UiState()
    }
} 