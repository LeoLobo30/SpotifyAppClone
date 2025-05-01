package br.com.example.spotify

import Routes
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.example.spotify.ui.screens.ListSongsScreen
import br.com.example.spotify.ui.screens.PlaySongScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ListSongs.name) {
        composable(Routes.ListSongs.name) {
            ListSongsScreen(navController)
        }
        composable(Routes.PlaySong.name + "/{idSong}", arguments = listOf(navArgument("idSong") {
            type = NavType.LongType
        })) {
            PlaySongScreen(navController, it.arguments?.getLong("idSong"))
        }
    }


}