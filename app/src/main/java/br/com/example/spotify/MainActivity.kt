package br.com.example.spotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import br.com.example.spotify.data.firebase.impl.RepositoryFirebaseFirestoreImpl
import br.com.example.spotify.data.room.database.SongDatabase
import br.com.example.spotify.ui.viewModel.ListSongsViewModel
import br.com.example.spotify.ui.viewModel.PlaySongViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinApplication(application = {
                androidContext(this@MainActivity)
                androidLogger()
                modules(initKoin())
            }) {
                MainApp()
            }
        }

    }

    private fun initKoin(): List<Module> {
        val roomModule = module {
            single {
                Room.databaseBuilder(androidContext(), SongDatabase::class.java, "song_database")
                    .build().songDAO()
            }
        }

        val viewModel = module {
            viewModel { ListSongsViewModel(get(), get()) }
            viewModel { PlaySongViewModel(get()) }
        }

        val firebaseModule = module {
            single { RepositoryFirebaseFirestoreImpl(false) }
        }

        return listOf(roomModule, viewModel, firebaseModule)
    }
}
