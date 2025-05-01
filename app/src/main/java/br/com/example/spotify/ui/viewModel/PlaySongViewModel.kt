package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.ViewModel
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.room.interfaces.SongDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaySongViewModel @Inject constructor(
    private val _songDAO: SongDAO
) : ViewModel() {

    private val _currentSong = MutableStateFlow<SongModel?>(null)
    val currentSong: MutableStateFlow<SongModel?> = _currentSong

    fun setSong(song: SongModel) {
        _currentSong.update { song }
    }

    suspend fun getSongById(id: Long) = withContext(Dispatchers.IO) {
        _songDAO.getSongById(id)
    }

    suspend fun getAllSongs() = withContext(Dispatchers.IO) {
        _songDAO.getAll()
    }



}
