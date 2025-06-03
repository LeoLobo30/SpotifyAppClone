package br.com.example.spotify.data.repository

import br.com.example.spotify.data.model.SongModel

interface SongRepository {
    suspend fun getAllSongs(): List<SongModel>
    suspend fun getSongById(id: Long): SongModel?
    suspend fun refreshSongs(): List<SongModel>
}