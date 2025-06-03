package br.com.example.spotify.ui.screens.playsong

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.intents.PlaySongIntent
import br.com.example.spotify.viewModel.PlaySongViewModel

@Composable
fun PlaySongScreen(
    navController: NavController,
    idSong: Long?,
    viewModel: PlaySongViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idSong) {
        idSong?.let {
            viewModel.processIntent(PlaySongIntent.LoadSong(it))
        }
    }

    PlaySongContent(
        currentSong = uiState.currentSong,
        isPlaying = uiState.isPlaying,
        seekBarPosition = uiState.seekBarPosition,
        songDuration = uiState.duration,
        onBack = { navController.popBackStack() },
        onPlayPause = { viewModel.processIntent(PlaySongIntent.PlayPause) },
        onNext = { viewModel.processIntent(PlaySongIntent.Next) },
        onPrevious = { viewModel.processIntent(PlaySongIntent.Previous) },
        onSeekTo = { viewModel.processIntent(PlaySongIntent.SeekTo(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongContent(
    currentSong: SongModel?,
    isPlaying: Boolean,
    seekBarPosition: Float,
    songDuration: Float,
    onBack: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeekTo: (Float) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Playing") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AlbumArtAndTitleSection(
                title = currentSong?.title,
                band = currentSong?.band
            )
            SeekBarSection(
                position = seekBarPosition,
                duration = songDuration,
                onSeekChanged = onSeekTo
            )
            PlaybackControlsSection(
                isPlaying = isPlaying,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaySongContentPreview() {
    val fakeSong = SongModel(
        id = 1L,
        title = "Preview Song",
        band = "Preview Band",
        songUrl = ""
    )
    PlaySongContent(
        currentSong = fakeSong,
        isPlaying = false,
        seekBarPosition = 30f,
        songDuration = 180f,
        onBack = {},
        onPlayPause = {},
        onNext = {},
        onPrevious = {},
        onSeekTo = {}
    )
}