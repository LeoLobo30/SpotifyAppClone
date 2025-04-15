package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.spotify.domain.usercases.GetSongsUseCase
import br.com.example.spotify.ui.state.SongUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val getSongsUseCase: GetSongsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<SongUiState>(SongUiState.Loading)
    val uiState: StateFlow<SongUiState> = _uiState

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        viewModelScope.launch {
            try {
                val songs = getSongsUseCase()
                _uiState.value = SongUiState.Success(songs)
            } catch (e: Exception) {
                _uiState.value = SongUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}