package com.example.submissionjetapackcompose.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.data.ArtistRepository
import com.example.submissionjetapackcompose.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ArtistRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Artist>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Artist>>>
        get() = _uiState

    fun getAllArtists(){
        viewModelScope.launch {
            repository.getAllArtists()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { artist ->
                    _uiState.value = UiState.Success(artist)
                }
        }
    }
    fun searchArtists(query: String) = repository.searchArtists(query)
}