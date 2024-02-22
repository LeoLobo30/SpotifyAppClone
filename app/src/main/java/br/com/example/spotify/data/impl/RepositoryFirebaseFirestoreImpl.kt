package br.com.example.spotify.data.impl

import br.com.example.spotify.data.interfaces.RepositoryFirebaseFirestore
import br.com.example.spotify.data.model.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RepositoryFirebaseFirestoreImpl : RepositoryFirebaseFirestore {

    override fun getInstance(): FirebaseFirestore {
        return Firebase.firestore
    }

    // 10.0.2.2 is the special IP address to connect to the 'localhost' of
    // the host computer from an Android emulator.
    override fun getEmulatorInstance(): FirebaseFirestore {
        val firestore = Firebase.firestore
        firestore.useEmulator("10.0.2.2", 8080)

        firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = false
        }

        return firestore

    }

    override suspend fun getAllSongs(firestore: FirebaseFirestore): List<SongModel> {
        val songsList = ArrayList<SongModel>()

        runBlocking {
            val response = firestore.collection("songs")
                .get().await()

                if (response != null) {
                    for (document in response) {
                        val songModel = document.toObject<SongModel>()
                        songsList.add(songModel)
                    }
                }
        }

        return songsList
    }

}