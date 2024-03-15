package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.example.spotify.data.firebase.impl.RepositoryFirebaseFirestoreImpl
import br.com.example.spotify.data.firebase.model.SongModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ListSongsViewModel : ViewModel() {

    private val repositoryFirebaseFirestoreImpl = RepositoryFirebaseFirestoreImpl(false)

    private val _listSongs = MutableLiveData<List<SongModel>>()
    val listSongs: LiveData<List<SongModel>> get() = _listSongs

    init {
        getSongs()
    }

    private fun getSongs() {
        CoroutineScope(Dispatchers.IO).launch() {
            _listSongs.postValue(repositoryFirebaseFirestoreImpl.getAllSongs())
        }
    }



}
