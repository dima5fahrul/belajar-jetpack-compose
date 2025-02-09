package com.example.submissionjetapackcompose.data

import com.example.submissionjetapackcompose.model.Artist
import com.example.submissionjetapackcompose.model.ArtistData.artistData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ArtistRepository {
    private val artists = mutableListOf<Artist>()

    init {
        if (artists.isEmpty()) {
            artistData.forEach {
                artists.add(it)
            }
        }
    }

    fun getAllArtists(): Flow<List<Artist>> {
        return flowOf(artists)
    }

    fun getArtistById(artistId: Long): Artist {
        return artists.first {
            it.id == artistId
        }
    }

    fun searchArtists(query: String): List<Artist>{
        return artistData.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getFavoriteArtists(): Flow<List<Artist>> {
        return getAllArtists()
            .map { orderRewards ->
                orderRewards.filter { artist ->
                    artist.isFavorite
                }
            }
    }

    fun searchFavoriteArtists(query: String): List<Artist>{
        return artistData.filter {
            it.name.contains(query, ignoreCase = true) && it.isFavorite
        }
    }

    fun updateFavorite(artistId: Long, newState: Boolean): Flow<Boolean> {
        val index = artists.indexOfFirst { it.id == artistId }
        val result = if (index >= 0) {
            val artist = artists[index]
            artists[index] = artist.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
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