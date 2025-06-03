package br.com.example.spotify.ui.screens.listsongs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.example.spotify.data.model.SongModel

@Composable
fun SongRow(song: SongModel, isSelected: Boolean, onPlayClick: () -> Unit) {
    val background = if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.background

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(background)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPlayClick) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play music",
                modifier = Modifier.size(35.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(
                text = "Title - ${song.title}",
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = "Band - ${song.band}")
        }
    }
}