package br.com.example.spotify.data.firebase.impl

import br.com.example.spotify.data.model.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getAllSongs(): List<SongModel> = withContext(Dispatchers.IO) {
        val list = mutableListOf<SongModel>()
        val snapshot = firestore.collection("songs").get().await()
        for (doc in snapshot) {
            doc.toObject(SongModel::class.java).let { list.add(it) }
        }
        list
    }
}