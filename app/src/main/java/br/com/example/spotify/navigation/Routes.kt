package br.com.example.spotify.navigation

sealed class Routes(val route: String) {
    data object ListSongs : Routes("list_songs")
    data object PlaySong : Routes("play_song/{idSong}") {
        fun createRoute(idSong: Long): String = "play_song/$idSong"
    }
}