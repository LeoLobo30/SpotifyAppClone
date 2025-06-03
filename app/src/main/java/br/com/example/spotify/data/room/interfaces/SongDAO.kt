package br.com.example.spotify.data.room.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.example.spotify.data.model.SongModel

@Dao
interface SongDAO {
    @Query("SELECT * FROM songs")
    suspend fun getAll(): List<SongModel>

    @Insert
    suspend fun insert(song: SongModel): Long

    @Update
    suspend fun update(song: SongModel)

    @Delete
    suspend fun delete(song: SongModel)

    @Query("DELETE FROM songs")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(songs: List<SongModel>)

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: Long): SongModel
}