package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.ViewModel
import br.com.example.spotify.data.room.interfaces.SongDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaySongViewModel(
    private val _songDAO: SongDAO
) : ViewModel() {

    suspend fun getSongById(id: Long) = withContext(Dispatchers.IO) {
        _songDAO.getSongById(id)
    }

    suspend fun getAllSongs() = withContext(Dispatchers.IO) {
        _songDAO.getAll()
    }

}
