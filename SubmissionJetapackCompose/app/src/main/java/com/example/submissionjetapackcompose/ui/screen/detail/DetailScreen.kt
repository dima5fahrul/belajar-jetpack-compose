package com.example.submissionjetapackcompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetapackcompose.R
import com.example.submissionjetapackcompose.ViewModelFactory
import com.example.submissionjetapackcompose.common.UiState
import com.example.submissionjetapackcompose.di.Injection
import com.example.submissionjetapackcompose.ui.theme.SubmissionJetapackComposeTheme

@Composable
fun DetailScreen(
    artistId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getArtistById(artistId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    title = data.name,
                    photo = data.photo,
                    description = data.description,
                    place = data.place,
                    onBackClick = navigateBack,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes photo: Int,
    title: String,
    description: String,
    place: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onBackClick()
                        }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
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

                Spacer(modifier = Modifier.height(8.dp))
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
                    Spacer(modifier = Modifier.height(4.dp))
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

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    SubmissionJetapackComposeTheme {
        DetailContent(
            photo = R.drawable.martingarrix,
            title = stringResource(R.string.martingarrix),
            place = stringResource(R.string.martingarrix_place),
            description = stringResource(R.string.martingarrix_desc),
            onBackClick = {},

            )
    }
}
