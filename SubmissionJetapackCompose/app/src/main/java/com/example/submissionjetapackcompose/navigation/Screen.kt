package com.example.submissionjetapackcompose.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object About : Screen("about")
    data object DetailArtist : Screen("home/{artistId}") {
        fun createRoute(artistId: Long) = "home/$artistId"
    }
}