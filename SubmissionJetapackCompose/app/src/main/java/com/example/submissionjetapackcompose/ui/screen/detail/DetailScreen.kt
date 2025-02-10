package com.example.submissionjetapackcompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetapackcompose.R
import com.example.submissionjetapackcompose.ViewModelFactory
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.di.Injection
import com.example.submissionjetapackcompose.model.Artist

@Composable
fun DetailScreen(
    artistId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            viewModel.getArtistById(artistId)
        }

        is UiState.Success -> {
            val data = (uiState as UiState.Success<Artist>).data
            DetailContent(
                photo = data.photo,
                title = data.name,
                description = data.description,
                place = data.place,
                onBackClick = navigateBack,
                isFavorite = data.isFavorite,
                onClick = {
                    viewModel.updateFavorite(data.id, data.isFavorite)
                },
            )
        }

        is UiState.Error -> {}
    }
}

@Composable
fun DetailContent(
    @DrawableRes photo: Int,
    title: String,
    description: String,
    place: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .testTag("scrollToBottom")
        ) {
            Box {
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .size(40.dp)
                        .testTag("back_home")
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                    )
                }
                IconButton(
                    onClick = onClick,
                    modifier = modifier
                        .padding(end = 16.dp, top = 8.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(Color.White)
                        .testTag("favorite_detail_button")
                ) {
                    Icon(
                        imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = if (!isFavorite) stringResource(R.string.add_to_favorite) else stringResource(
                            R.string.remove_from_favorite
                        ),
                        tint = if (!isFavorite) Color.Black else Color.Red
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = place,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = modifier.height(8.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.description),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    Text(
                        text = description,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
