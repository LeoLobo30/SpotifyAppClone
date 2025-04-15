package br.com.example.spotify.domain.repository

import br.com.example.spotify.data.source.FirebaseSource
import br.com.example.spotify.domain.model.Song
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(private val firebaseSource: FirebaseSource) : MusicRepository {
    override suspend fun getSongs(): List<Song> = firebaseSource.fetchSongs()
}