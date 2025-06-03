package br.com.example.spotify.di

import android.content.Context
import androidx.room.Room
import br.com.example.spotify.data.firebase.impl.FirestoreProvider
import br.com.example.spotify.data.firebase.impl.SongRemoteDataSource
import br.com.example.spotify.data.repository.SongRepository
import br.com.example.spotify.data.repository.impl.SongRepositoryImpl
import br.com.example.spotify.data.room.database.SongDatabase
import br.com.example.spotify.data.room.interfaces.SongDAO
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): SongDatabase {
        return Room.databaseBuilder(app, SongDatabase::class.java, "song_database")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideSongDAO(db: SongDatabase): SongDAO = db.songDAO()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirestoreProvider(useEmulator = false).getFirestore()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(firestore: FirebaseFirestore): SongRemoteDataSource {
        return SongRemoteDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideSongRepository(
        songDAO: SongDAO,
        remoteDataSource: SongRemoteDataSource
    ): SongRepository {
        return SongRepositoryImpl(songDAO, remoteDataSource)
    }
}
