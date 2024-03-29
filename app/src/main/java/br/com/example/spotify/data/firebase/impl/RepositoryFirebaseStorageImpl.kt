package br.com.example.spotify.data.firebase.impl

import br.com.example.spotify.data.firebase.interfaces.RepositoryFirebase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class RepositoryFirebaseStorageImpl : RepositoryFirebase<FirebaseStorage> {

    override fun getInstance(): FirebaseStorage {
        return Firebase.storage
    }

    // 10.0.2.2 is the special IP address to connect to the 'localhost' of
    // the host computer from an Android emulator.
    override fun getEmulatorInstance(): FirebaseStorage {
        val storage = Firebase.storage
        storage.useEmulator("10.0.2.2", 9199)
        return storage
    }
}