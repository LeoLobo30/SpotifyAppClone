package br.com.example.spotify.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.room.interfaces.SongDAO

@Database(entities = [SongModel::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDAO(): SongDAO
}

