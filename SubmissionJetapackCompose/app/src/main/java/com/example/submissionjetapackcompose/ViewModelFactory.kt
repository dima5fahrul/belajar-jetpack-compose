package com.example.submissionjetapackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionjetapackcompose.data.ArtistRepository
import com.example.submissionjetapackcompose.ui.screen.detail.DetailViewModel
import com.example.submissionjetapackcompose.ui.screen.favorite.FavoriteViewModel
import com.example.submissionjetapackcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: ArtistRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}