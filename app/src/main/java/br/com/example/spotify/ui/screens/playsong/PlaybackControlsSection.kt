package br.com.example.spotify.ui.screens.playsong

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.example.spotify.R

@Composable
fun PlaybackControlsSection(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onPrevious) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_circle_left_24),
                contentDescription = "Previous"
            )
        }
        IconButton(onClick = onPlayPause) {
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
        IconButton(onClick = onNext) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_circle_right_24),
                contentDescription = "Next"
            )
        }
    }
}