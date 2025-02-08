package com.example.submissionjetapackcompose.di

import com.example.submissionjetapackcompose.data.ArtistRepository

object Injection {
    fun provideRepository(): ArtistRepository {
        return ArtistRepository.getInstance()
    }
}