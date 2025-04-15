package br.com.example.spotify.di

import android.content.Context
import androidx.room.Room
import br.com.example.spotify.data.firebase.impl.RepositoryFirebaseFirestoreImpl
import br.com.example.spotify.data.room.database.SongDatabase
import br.com.example.spotify.data.room.interfaces.SongDAO
import br.com.example.spotify.data.source.FirebaseSource
import br.com.example.spotify.domain.repository.MusicRepository
import br.com.example.spotify.domain.repository.MusicRepositoryImpl
import br.com.example.spotify.domain.usercases.GetSongsUseCase
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
    fun provideFirebaseRepository(): RepositoryFirebaseFirestoreImpl {
        return RepositoryFirebaseFirestoreImpl(false)
    }

    @Provides
    fun provideFirebaseSource(): FirebaseSource = FirebaseSource()

    @Provides
    fun provideMusicRepository(firebaseSource: FirebaseSource): MusicRepository =
        MusicRepositoryImpl(firebaseSource)

    @Provides
    fun provideGetSongsUseCase(repository: MusicRepository): GetSongsUseCase =
        GetSongsUseCase(repository)
}
