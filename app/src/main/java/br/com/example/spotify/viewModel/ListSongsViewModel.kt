package br.com.example.spotify.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSongsViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _songs = MutableStateFlow<List<SongModel>>(emptyList())
    val songs: StateFlow<List<SongModel>> = _songs

    private val _selectedId = MutableStateFlow<Long?>(null)
    val selectedId: StateFlow<Long?> = _selectedId

    fun loadSongs() {
        viewModelScope.launch {
            val local = songRepository.getAllSongs()
            _songs.value = local.ifEmpty { songRepository.refreshSongs() }
        }
    }

    fun select(id: Long) {
        _selectedId.value = id
    }
}