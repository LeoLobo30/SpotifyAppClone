package br.com.example.spotify.data.firebase.interfaces

import com.google.firebase.ktx.Firebase

interface RepositoryFirebase<T> {

    fun getInstance(): T

    fun getEmulatorInstance(): T


}