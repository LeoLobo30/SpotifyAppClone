package br.com.example.spotify.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import br.com.example.spotify.R
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.ui.viewModel.PlaySongViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

private val songStateFlow = MutableStateFlow<SongModel?>(null)

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongScreen(
    navController: NavController,
    idSong: Long?,
    playSongViewModel: PlaySongViewModel = koinViewModel()
) {
    val context = LocalContext.current

    var allSongs: List<SongModel> by remember { mutableStateOf(listOf()) }

    LaunchedEffect(null) {
        allSongs = playSongViewModel.getAllSongs()
        songStateFlow.value = allSongs.find { it.id == idSong }
    }

    val song: SongModel? by songStateFlow.collectAsState()

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(null) {
        PlayerView(context).player = exoPlayer
        if (song != null) {
            val mediaItem = song?.songUrl?.let { MediaItem.fromUri(it) }
            exoPlayer.setMediaItem(mediaItem ?: MediaItem.EMPTY)
        }
        exoPlayer.prepare()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Play") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Cabeçalho com capa do álbum e título da música
            AlbumArtAndTitleSection(song?.title, song?.band)
            // Barra de progresso da música
            SeekBarSection()
            // Controles de reprodução (play, pause, stop)
            PlaybackControlsSection(exoPlayer, allSongs)
        }
    }

}

@Composable
fun AlbumArtAndTitleSection(title: String?, band: String?) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Capa do álbum",
            modifier = Modifier
                .size(250.dp)
                .background(Color.Black)
                .border(10.dp, MaterialTheme.colorScheme.primary)
        )
        Text(
            text = title ?: "Nenhuma música selecionada",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp,
        )
        Text(
            text = band ?: "Nenhuma banda selecionada",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun PlaybackControlsSection(
    exoPlayer: ExoPlayer,
    allSongs: List<SongModel>?
) {
    val playOrPause = remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        IconButton(onClick = {
            try {
                songStateFlow.value = allSongs?.get(allSongs.indexOf(songStateFlow.value) - 1)
            } catch (_: IndexOutOfBoundsException) {
            }
            exoPlayer.setMediaItem(MediaItem.fromUri(songStateFlow.value?.songUrl ?: ""))
            exoPlayer.stop()
            exoPlayer.prepare()
            playOrPause.value = true
        }) {
            Icon(
                painterResource(R.drawable.baseline_arrow_circle_left_24),
                contentDescription = "backSong"
            )
        }
        IconButton(onClick = { playOrPause.value = !playOrPause.value }) {
            if (playOrPause.value) {
                Icon(
                    painterResource(R.drawable.baseline_pause_circle_24),
                    contentDescription = "Play"
                )
                exoPlayer.play()
            } else {
                Icon(
                    painterResource(R.drawable.baseline_play_circle_24),
                    contentDescription = "Pause"
                )
                exoPlayer.pause()
            }
        }
        IconButton(onClick = {
            try {
                songStateFlow.value = allSongs?.get(allSongs.indexOf(songStateFlow.value) + 1)
            } catch (_: IndexOutOfBoundsException) {
            }
            exoPlayer.setMediaItem(MediaItem.fromUri(songStateFlow.value?.songUrl ?: ""))
            exoPlayer.stop()
            exoPlayer.prepare()
            playOrPause.value = true
        }) {
            Icon(
                painterResource(R.drawable.baseline_arrow_circle_right_24),
                contentDescription = "nextSong"
            )
        }
    }
}

@Composable
fun SeekBarSection() {
    val isPlaying = remember { mutableStateOf(false) }
    val seekBarValue = remember { mutableFloatStateOf(0f) }
    val songDuration = 100 // Duração total da música em segundos

    LaunchedEffect(key1 = isPlaying.value) {
        if (isPlaying.value) {
            // Atualizar o valor da seekbar periodicamente enquanto a música está tocando
            while (seekBarValue.value < songDuration.toFloat()) {
                seekBarValue.value += 0.1f // Ajuste o valor de acordo com a taxa de atualização desejada
                delay(100) // Ajuste o intervalo de atualização
            }
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = formatTime(seekBarValue.value),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        // Barra de progresso
        Slider(
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTickColor = Color.White,
                inactiveTickColor = Color.White
            ),
            value = seekBarValue.value,
            onValueChange = { seekBarValue.value = it },
            valueRange = 0f..songDuration.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Text(
            text = formatTime(songDuration.toFloat()),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun formatTime(seconds: Float): String {
    val minutes = (seconds / 60).toInt()
    val secondsFormatted = "%.0f".format(seconds % 60)
    return "$minutes:$secondsFormatted"
}
