package com.example.submissionjetapackcompose.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetapackcompose.R
import com.example.submissionjetapackcompose.ViewModelFactory
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.di.Injection
import com.example.submissionjetapackcompose.model.Artist
import com.example.submissionjetapackcompose.ui.components.ArtistItem
import com.example.submissionjetapackcompose.ui.components.HomeTopBar
import com.example.submissionjetapackcompose.ui.components.ScrollToTopButton
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val showButton: Boolean by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 0
        }
    }
    val query by viewModel.query

    Scaffold(
        topBar = {
            HomeTopBar(query, viewModel::search)
        }
    ) { innerPadding ->
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getFavoriteArtists()
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("empty_favorite"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_favorite_artist),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    } else {
                        FavoriteContent(
                            artist = uiState.data,
                            innerPadding = innerPadding,
                            navigateToDetail = navigateToDetail,
                            showButton = showButton,
                            gridState = gridState,
                            onClickTop = {
                                scope.launch {
                                    gridState.animateScrollToItem(0)
                                }
                            }
                        )
                    }
                }

                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun FavoriteContent(
    artist: List<Artist>,
    innerPadding: PaddingValues,
    navigateToDetail: (Long) -> Unit,
    showButton: Boolean = false,
    gridState: LazyGridState,
    onClickTop: () -> Unit = {}
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .testTag("lazy_favorite_grid")
    ) {
        items(artist, key = { it.id }) { data ->
            ArtistItem(
                name = data.name,
                photo = data.photo,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically(),
        modifier = Modifier
            .padding(bottom = 30.dp)
    ) {
        ScrollToTopButton(
            onClick = {
                onClickTop()
            }
        )
    }
}