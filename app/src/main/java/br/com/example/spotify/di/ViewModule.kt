// PlayerModule.kt
package br.com.example.spotify.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModule {

    @Provides
    @ViewModelScoped
    fun provideExoPlayer(@ApplicationContext app: Context): ExoPlayer {
        return ExoPlayer.Builder(app).build()
    }
}