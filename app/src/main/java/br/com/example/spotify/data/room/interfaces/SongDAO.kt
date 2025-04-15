package br.com.example.spotify.data.room.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.example.spotify.data.model.SongModel
import javax.inject.Inject

@Dao
interface SongDAO {
    @Query("SELECT * FROM songs")
    fun getAll(): List<SongModel>

    @Insert
    fun insert(song: SongModel): Long

    @Update
    fun update(song: SongModel)

    @Delete
    fun delete(song: SongModel)

    @Query("DELETE FROM songs")
    fun deleteAll()

    @Insert
    fun insertAll(songs: List<SongModel>)

    @Query("SELECT * FROM songs WHERE id = :id")
    fun getSongById(id: Long): SongModel
}