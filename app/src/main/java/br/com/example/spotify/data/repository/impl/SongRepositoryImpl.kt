package br.com.example.spotify.data.repository.impl

import br.com.example.spotify.data.firebase.impl.SongRemoteDataSource
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.repository.SongRepository
import br.com.example.spotify.data.room.interfaces.SongDAO
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDAO: SongDAO,
    private val remoteDataSource: SongRemoteDataSource
) : SongRepository {

    override suspend fun getAllSongs(): List<SongModel> {
        val localSongs = songDAO.getAll()
        return localSongs.ifEmpty {
            val remoteSongs = remoteDataSource.getAllSongs()
            songDAO.deleteAll()
            songDAO.insertAll(remoteSongs)
            remoteSongs
        }
    }

    override suspend fun refreshSongs(): List<SongModel> {
        val songs = remoteDataSource.getAllSongs()
        songDAO.deleteAll()
        songDAO.insertAll(songs)
        return songs
    }

    override suspend fun getSongById(id: Long): SongModel {
        return songDAO.getSongById(id)
    }
}