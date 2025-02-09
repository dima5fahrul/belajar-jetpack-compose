package com.example.submissionjetapackcompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetapackcompose.ViewModelFactory
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.di.Injection
import com.example.submissionjetapackcompose.model.Artist
import com.example.submissionjetapackcompose.ui.components.ArtistItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllArtists()
            }

            is UiState.Success -> {
                HomeContent(
                    artist = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    artist: List<Artist>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(artist) { data ->
            ArtistItem(
                name = data.name,
                photo = data.photo,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
}