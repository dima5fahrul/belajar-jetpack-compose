package com.example.submissionjetapackcompose.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        }
    )
}