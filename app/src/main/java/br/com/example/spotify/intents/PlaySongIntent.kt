package br.com.example.spotify.intents

sealed class PlaySongIntent {
    data class LoadSong(val id: Long) : PlaySongIntent()
    data object PlayPause : PlaySongIntent()
    data class SeekTo(val position: Float) : PlaySongIntent()
    data object Next : PlaySongIntent()
    data object Previous : PlaySongIntent()
}