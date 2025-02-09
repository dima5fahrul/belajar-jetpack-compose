package com.example.submissionjetapackcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = UiState.Success(repository.searchArtists(_query.value))
    }
}