package com.example.submissionjetapackcompose.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submissionjetapackcompose.R
import com.example.submissionjetapackcompose.ui.theme.SubmissionJetapackComposeTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val openHandler = LocalUriHandler.current

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Image(
            painter = painterResource(id = R.drawable.me),
            contentDescription = stringResource(R.string.me),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(R.string.dimas_fahrul),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Text(
            text = stringResource(R.string.dfahrul07_gmail_com),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = stringResource(R.string.dimasfahrul_my_id),
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier.clickable {
                openHandler.openUri("https://dimasfahrul.my.id")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    SubmissionJetapackComposeTheme {
        AboutScreen()
    }
}