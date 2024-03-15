package br.com.example.spotify.ui.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
    fun PlaySongScreen(navController: NavController, title: String?) {

        Button(onClick = { navController.popBackStack()}) {
            Text(text = "Go back to list of songs + $title")
        }

    }
