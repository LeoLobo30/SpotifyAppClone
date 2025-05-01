package br.com.example.spotify.ui.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.example.spotify.R
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.ui.components.topbar.TopBar
import br.com.example.spotify.ui.viewModel.PlaySongViewModel
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction1
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun PlaySongScreen(
    navController: NavController,
    idSong: Long?,
    playSongViewModel: PlaySongViewModel = hiltViewModel()
) {
    val song by playSongViewModel.currentSong.collectAsState()
    var allSongs: List<SongModel> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        allSongs = playSongViewModel.getAllSongs()
        allSongs.find { it.id == idSong }?.let { playSongViewModel.setSong(it) }
    }

    ContentPlaySong(
        navController = navController,
        song = song,
        allSongs = allSongs,
        setSong = playSongViewModel::setSong
    )
}


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun ContentPlaySong(
    navController: NavController,
    song: SongModel?,
    allSongs: List<SongModel>,
    setSong: (SongModel) -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(song) {
        if (song != null) {
            val mediaItem = MediaItem.fromUri(song.songUrl ?: "")
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    TopBar(
        onBackPressed = {
            navController.popBackStack()
            exoPlayer.release()
        },
        title = "Play"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AlbumArtAndTitleSection(song?.title, song?.band)
            SeekBarSection(exoPlayer)
            PlaybackControlsSection(
                exoPlayer = exoPlayer,
                allSongs = allSongs,
                setSong = setSong,
                currentSong = song
            )
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
            // TODO: add custom image
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
    allSongs: List<SongModel>,
    setSong: (SongModel) -> Unit,
    currentSong: SongModel?
) {
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(newIsPlaying: Boolean) {
                isPlaying = newIsPlaying
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        IconButton(onClick = {
            val index = allSongs.indexOf(currentSong)
            val prevSong = allSongs.getOrNull(index - 1)
            prevSong?.let {
                setSong(it)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_circle_left_24),
                contentDescription = "Previous Song"
            )
        }

        IconButton(onClick = {
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }) {
            Icon(
                painterResource(
                    id = if (isPlaying)
                        R.drawable.baseline_pause_circle_24
                    else
                        R.drawable.baseline_play_circle_24
                ),
                contentDescription = if (isPlaying) "Pause" else "Play"
            )
        }

        IconButton(onClick = {
            val index = allSongs.indexOf(currentSong)
            val nextSong = allSongs.getOrNull(index + 1)
            nextSong?.let {
                setSong(it)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_circle_right_24),
                contentDescription = "Next Song"
            )
        }
    }
}

@Composable
fun SeekBarSection(exoPlayer: ExoPlayer) {

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }  // Track playback state
    var seekBarValue by remember { mutableFloatStateOf(0f) } // Current seek bar position
    var songDuration by remember { mutableFloatStateOf(100f) } // Duration in seconds (converted to float)

    val convertToMilliSeconds = 1000
    val convertToSeconds = 1000f

    LaunchedEffect(Unit) {
        exoPlayer.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) { // Update seek bar position
                        songDuration = exoPlayer.duration.toDuration(DurationUnit.MILLISECONDS)
                            .toLong(DurationUnit.MILLISECONDS).toFloat()
                        isPlaying = exoPlayer.isPlaying
                    } else if (playbackState == Player.STATE_ENDED) {
                        exoPlayer.seekTo(0)
                    }
                    super.onPlaybackStateChanged(playbackState)
                }
            }
        )

        while (true) {
            seekBarValue = exoPlayer.currentPosition.toFloat() / convertToSeconds
            delay(1000) // Adjust delay for desired update frequency
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = FormatTime(seekBarValue),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        // Seek bar with custom colors
        Slider(
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondary
            ),
            value = seekBarValue,
            onValueChange = { newValue ->
                seekBarValue = newValue
                exoPlayer.seekTo((newValue * 1000).toLong())
            },
            valueRange = 0f..songDuration / convertToSeconds,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun FormatTime(seconds: Float): String {
    val minutes = (seconds / 60).toInt()
    val secondsFormatted = "%.0f".format(seconds % 60)
    return "${minutes}m : ${secondsFormatted}s"
}


@Preview(showBackground = true)
@Composable
fun ContentPlaySongPreview() {
    val navController = rememberNavController()
    val playSongViewModel: PlaySongViewModel = hiltViewModel()

    // Fake song for preview
    val fakeSong = SongModel(
        id = 1L,
        title = "Preview Song",
        band = "Preview Band",
        songUrl = "https://example.com/fake.mp3"
    )

    val fakeSongList = listOf(
        fakeSong,
        SongModel(
            id = 2L,
            title = "Next Preview Song",
            band = "Another Band",
            songUrl = "https://example.com/next.mp3"
        )
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        ContentPlaySong(
            navController = navController,
            song = fakeSong,
            allSongs = fakeSongList,
            setSong = playSongViewModel::setSong
        )
    }
}