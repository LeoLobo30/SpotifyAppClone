package br.com.example.spotify.data.interfaces

import br.com.example.spotify.data.model.SongModel
import com.google.firebase.firestore.FirebaseFirestore

interface RepositoryFirebaseFirestore {

    fun getInstance(): FirebaseFirestore

    fun getEmulatorInstance(): FirebaseFirestore

    suspend fun getAllSongs(firestore: FirebaseFirestore): List<SongModel>
}