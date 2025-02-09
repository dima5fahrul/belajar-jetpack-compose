package com.example.submissionjetapackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.data.ArtistRepository
import com.example.submissionjetapackcompose.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow

class DetailViewModel(
    private val repository: ArtistRepository
): ViewModel() {
    private val _uiSTate: MutableStateFlow<UiState<Artist>> = MutableStateFlow(UiState.Loading)
    val uiState: MutableStateFlow<UiState<Artist>>
        get() = _uiSTate

    fun getArtistById(artistId: Long) {
        val artist = repository.getArtistById(artistId)
        _uiSTate.value = UiState.Success(artist)
    }
}