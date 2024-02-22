package br.com.example.spotify.data.interfaces

import com.google.firebase.storage.FirebaseStorage

interface RepositoryFirebaseStorage {

    fun getInstance(): FirebaseStorage

    fun getEmulatorInstance(): FirebaseStorage


}