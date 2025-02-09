package com.example.submissionjetapackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.data.ArtistRepository
import com.example.submissionjetapackcompose.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ArtistRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Artist>> = MutableStateFlow(UiState.Loading)
    val uiState: MutableStateFlow<UiState<Artist>>
        get() = _uiState

    fun getArtistById(artistId: Long) {
        viewModelScope.launch {
            val artist = repository.getArtistById(artistId)
            _uiState.value = UiState.Success(artist)
        }
    }

    fun updateFavorite(id: Long, newState: Boolean) = viewModelScope.launch {
        repository.updateFavorite(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getArtistById(id)
            }
    }
}