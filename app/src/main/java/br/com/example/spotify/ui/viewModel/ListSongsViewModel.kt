package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.example.spotify.data.firebase.impl.RepositoryFirebaseFirestoreImpl
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.room.interfaces.SongDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ListSongsViewModel(
    private val songDAO: SongDAO,
    private val repositoryFirebaseFirestoreImpl: RepositoryFirebaseFirestoreImpl
) : ViewModel() {

    private val _listSongs = MutableLiveData<List<SongModel>>()
    val listSongs: LiveData<List<SongModel>> get() = _listSongs

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (songDAO.getAll().isEmpty()) {
                fetchSongsFromFirebaseAndStoreLocally()
            } else {
                _listSongs.postValue(songDAO.getAll())
            }
        }
    }

    private fun fetchSongsFromFirebaseAndStoreLocally() {
        CoroutineScope(Dispatchers.IO).launch {
            val listSongsTemp = repositoryFirebaseFirestoreImpl.getAllSongs()
            _listSongs.postValue(listSongsTemp)
            songDAO.deleteAll()
            songDAO.insertAll(listSongsTemp)
        }
    }



}
