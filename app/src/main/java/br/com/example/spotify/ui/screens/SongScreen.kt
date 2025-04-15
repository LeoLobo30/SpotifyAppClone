package br.com.example.spotify.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.example.spotify.ui.state.SongUiState
import br.com.example.spotify.ui.viewModel.SongViewModel

@Composable
fun SongScreen(viewModel: SongViewModel = hiltViewModel()) {
    when (val state = viewModel.uiState.collectAsState().value) {
        is SongUiState.Loading -> CircularProgressIndicator()
        is SongUiState.Success -> LazyColumn {
            items(state.songs) { song ->
                Text(text = song.title)
            }
        }
        is SongUiState.Error -> Text("Error: ${state.message}")
    }
}