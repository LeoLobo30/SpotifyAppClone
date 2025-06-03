package br.com.example.spotify.ui.screens.playsong

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SeekBarSection(
    position: Float,
    duration: Float,
    onSeekChanged: (Float) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = formatTime(position))
        Slider(
            value = position,
            onValueChange = onSeekChanged,
            valueRange = 0f..duration,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
        Text(text = formatTime(duration))
    }
}

fun formatTime(seconds: Float): String {
    val min = (seconds / 60).toInt()
    val sec = (seconds % 60).toInt()
    return "%02d:%02d".format(min, sec)
}