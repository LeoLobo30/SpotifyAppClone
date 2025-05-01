package br.com.example.spotify.ui.screens

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.example.spotify.ui.components.topbar.TopBar
import br.com.example.spotify.ui.viewModel.ListSongsViewModel

@Composable
fun ListSongsScreen(navController: NavController) {
    val listSongsViewModel: ListSongsViewModel = hiltViewModel()
    val listSongs by listSongsViewModel.listSongs.observeAsState(emptyList())

    TopBar(
        onBackPressed = null,
        title = "List songs"
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            for (song in listSongs) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(60f)),
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = {
                            navController.navigate(Routes.PlaySong.name + "/${song.id}")
                        }) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "play music",
                                Modifier.size(Dp(35f))
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = Dp(20f))
                    ) {
                        Text(
                            text = "title - ${song.title}",
                            modifier = Modifier.padding(bottom = Dp(5f))
                        )
                        Text(text = "band - ${song.band}")
                    }
                }
                HorizontalDivider()
            }
        }
    }
}

