package br.com.example.spotify.ui.view

import Constants
import Routes
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.navArgument
import br.com.example.spotify.MainActivity
import br.com.example.spotify.data.firebase.model.SongModel
import br.com.example.spotify.ui.viewModel.ListSongsViewModel

    @Composable
    fun ListSongsScreen(navController: NavController) {
        val myActivity : MainActivity = LocalContext.current as MainActivity
        val listSongsViewModel = remember {
            ListSongsViewModel()
        }
        var listSongs : List<SongModel> by remember {
            mutableStateOf(listOf())
        }
        myActivity.apply { ->
            listSongsViewModel.listSongs.observe(this) {
                listSongs = it
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            for (song in listSongs){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(60f)),
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = { navController.navigate(route = Routes.PlaySong.name + "/${song.title}") }) {
                            Icon(
                                Icons.Filled.PlayArrow, contentDescription = "play music",
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
                Divider()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ListSongsPreview() {
        Column(modifier = Modifier.fillMaxSize()) {
            for (i in 0..9) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(60f)),
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Filled.PlayArrow, contentDescription = "play music",
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
                            text = "title - link park",
                            modifier = Modifier.padding(bottom = Dp(5f))
                        )
                        Text(text = "band - link park")
                    }
                }
                Divider()
            }
        }
    }
