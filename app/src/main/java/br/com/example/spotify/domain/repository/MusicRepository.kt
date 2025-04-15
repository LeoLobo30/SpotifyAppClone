package br.com.example.spotify.domain.repository

import br.com.example.spotify.domain.model.Song

interface MusicRepository {
    suspend fun getSongs(): List<Song>
}