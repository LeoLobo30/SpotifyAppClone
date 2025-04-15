package br.com.example.spotify.data.source

import br.com.example.spotify.domain.model.Song
import kotlinx.coroutines.delay

class FirebaseSource {
    suspend fun fetchSongs(): List<Song> {
        // Mocked delay for demo
        delay(1000)
        return listOf(
            Song("1", "Title 1", "Artist 1", "url1"),
            Song("2", "Title 2", "Artist 2", "url2")
        )
    }
}