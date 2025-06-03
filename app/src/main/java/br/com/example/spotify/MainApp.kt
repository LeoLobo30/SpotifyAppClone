package br.com.example.spotify

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.example.spotify.navigation.Routes
import br.com.example.spotify.ui.screens.listsongs.ListSongsScreen
import br.com.example.spotify.ui.screens.playsong.PlaySongScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ListSongs.route) {

        composable(Routes.ListSongs.route) {
            ListSongsScreen(navController)
        }

        composable(
            route = Routes.PlaySong.route,
            arguments = listOf(navArgument("idSong") {
                type = NavType.LongType
            })
        ) {
            val idSong = it.arguments?.getLong("idSong")
            PlaySongScreen(navController, idSong)
        }
    }
}