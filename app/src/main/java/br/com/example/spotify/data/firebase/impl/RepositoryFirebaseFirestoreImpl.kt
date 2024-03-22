package br.com.example.spotify.data.firebase.impl

import android.util.Log
import br.com.example.spotify.data.firebase.interfaces.RepositoryFirebase
import br.com.example.spotify.data.model.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class RepositoryFirebaseFirestoreImpl(emulator: Boolean) : RepositoryFirebase<FirebaseFirestore> {

    private var firebaseFirestore: FirebaseFirestore? = null

    init {
        firebaseFirestore = if (!emulator) {
            getInstance()
        } else {
            getEmulatorInstance()
        }
    }

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

    suspend fun getAllSongs(): List<SongModel> = withContext(Dispatchers.IO) {
        val songsList = mutableListOf<SongModel>()

        val response = firebaseFirestore?.collection("songs")?.get()?.await()

        if (response != null) {
            for (document in response) {
                val songModel = document.toObject<SongModel>()
                songsList.add(songModel)
            }
        } else {
            Log.w(
                RepositoryFirebaseFirestoreImpl::class.java.simpleName,
                "Error getting all songs."
            )
        }

        yield()
        songsList
    }

}