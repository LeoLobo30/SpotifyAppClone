package br.com.example.spotify.ui.screens.listsongs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.navigation.Routes
import br.com.example.spotify.viewModel.ListSongsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSongsScreen(navController: NavController) {
    val viewModel: ListSongsViewModel = hiltViewModel()
    val listSongs by viewModel.songs.collectAsState()
    val selectedId by viewModel.selectedId.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadSongs()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List songs") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        ListSongsContent(
            songs = listSongs,
            selectedSongId = selectedId,
            innerPadding = innerPadding,
            onPlayClick = { songId ->
                viewModel.select(songId)
                navController.navigate(Routes.PlaySong.createRoute(songId))
            }
        )
    }
}

@Composable
fun ListSongsContent(
    songs: List<SongModel>,
    selectedSongId: Long?,
    innerPadding: PaddingValues,
    onPlayClick: (Long) -> Unit
) {
    if (songs.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(songs) { song ->
                SongRow(
                    song = song,
                    isSelected = song.id == selectedSongId,
                    onPlayClick = { onPlayClick(song.id) }
                )
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ListSongsContentPreview() {
    val mockSongs = listOf(
        SongModel(1, "Song 1", "Band 1", ""),
        SongModel(2, "Song 2", "Band 2", "")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List songs") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        ListSongsContent(
            songs = mockSongs,
            innerPadding = innerPadding,
            onPlayClick = {},
            selectedSongId = null
        )
    }
}
