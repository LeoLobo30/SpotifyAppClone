package br.com.example.spotify.ui.state

import br.com.example.spotify.domain.model.Song

sealed class SongUiState {
    object Loading : SongUiState()
    data class Success(val songs: List<Song>) : SongUiState()
    data class Error(val message: String) : SongUiState()
}