package br.com.example.spotify.data.firebase.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreProvider(private val useEmulator: Boolean = false) {

    fun getFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore

        if (useEmulator) {
            firestore.useEmulator("10.0.2.2", 8080)
            firestore.firestoreSettings = firestoreSettings {
                PersistentCacheSettings.newBuilder()
                    .setSizeBytes(20 * 1024 * 1024)
                    .build()
            }
        }

        return firestore
    }
}