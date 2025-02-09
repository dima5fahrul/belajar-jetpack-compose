package com.example.submissionjetapackcompose.data

import com.example.submissionjetapackcompose.model.Artist
import com.example.submissionjetapackcompose.model.ArtistData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ArtistRepository {
    private val artists = ArtistData.artistData

    fun getAllArtists(): Flow<List<Artist>> {
        return flowOf(artists)
    }

    fun getArtistById(artistId: Long): Artist {
        return artists.first {
            it.id == artistId
        }
    }

    fun searchArtists(query: String): List<Artist>{
        return ArtistData.artistData.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: ArtistRepository? = null

        fun getInstance(): ArtistRepository =
            instance ?: synchronized(this) {
                ArtistRepository().apply {
                    instance = this
                }
            }
    }
}