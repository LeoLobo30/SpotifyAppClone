package br.com.example.spotify.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.example.spotify.data.firebase.impl.RepositoryFirebaseFirestoreImpl
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.room.interfaces.SongDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSongsViewModel @Inject constructor(
    private val _songDAO: SongDAO,
    private val _repositoryFirebaseFirestoreImpl: RepositoryFirebaseFirestoreImpl
) : ViewModel() {

    private val _listSongs = MutableLiveData<List<SongModel>>()
    val listSongs: LiveData<List<SongModel>> get() = _listSongs

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (_songDAO.getAll().isEmpty()) {
                fetchSongsFromFirebaseAndStoreLocally()
            } else {
                _listSongs.postValue(_songDAO.getAll())
            }
        }
    }

    private fun fetchSongsFromFirebaseAndStoreLocally() {
        CoroutineScope(Dispatchers.IO).launch {
            val listSongsTemp = _repositoryFirebaseFirestoreImpl.getAllSongs()
            _listSongs.postValue(listSongsTemp)
            _songDAO.deleteAll()
            _songDAO.insertAll(listSongsTemp)
        }
    }



}
