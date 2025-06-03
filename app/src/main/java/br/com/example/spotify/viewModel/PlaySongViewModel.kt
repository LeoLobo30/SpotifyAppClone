package br.com.example.spotify.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import br.com.example.spotify.data.model.SongModel
import br.com.example.spotify.data.repository.SongRepository
import br.com.example.spotify.intents.PlaySongIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaySongViewModel @Inject constructor(
    application: Application,
    private val songRepository: SongRepository,
    private val exoPlayer: ExoPlayer
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PlaySongUiState())
    val uiState: StateFlow<PlaySongUiState> = _uiState

    fun processIntent(intent: PlaySongIntent) {
        when (intent) {
            is PlaySongIntent.LoadSong -> loadAndPlay(intent.id)
            is PlaySongIntent.PlayPause -> togglePlayPause()
            is PlaySongIntent.SeekTo -> seekTo(intent.position)
            is PlaySongIntent.Next -> playNext()
            is PlaySongIntent.Previous -> playPrevious()
        }
    }

    private fun loadAndPlay(id: Long) {
        viewModelScope.launch {
            val songs = songRepository.getAllSongs()
            val song = songs.find { it.id == id }
            song?.let {
                exoPlayer.stop()
                exoPlayer.clearMediaItems()
                exoPlayer.setMediaItem(MediaItem.fromUri(it.songUrl))
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true

                updateUi {
                    copy(
                        currentSong = it,
                        seekBarPosition = 0f,
                        duration = 0f
                    )
                }
            }
        }
    }

    private fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    private fun seekTo(position: Float) {
        exoPlayer.seekTo((position * 1000).toLong())
        updateUi { copy(seekBarPosition = position) }
    }

    private fun playNext() {
        viewModelScope.launch {
            val idx = _uiState.value.currentSong?.let { current ->
                songRepository.getAllSongs().indexOfFirst { it.id == current.id }
            } ?: return@launch

            songRepository.getAllSongs().getOrNull(idx + 1)?.let {
                processIntent(PlaySongIntent.LoadSong(it.id))
            }
        }
    }

    private fun playPrevious() {
        viewModelScope.launch {
            val idx = _uiState.value.currentSong?.let { current ->
                songRepository.getAllSongs().indexOfFirst { it.id == current.id }
            } ?: return@launch

            songRepository.getAllSongs().getOrNull(idx - 1)?.let {
                processIntent(PlaySongIntent.LoadSong(it.id))
            }
        }
    }

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val durMs = exoPlayer.duration
                    if (durMs != C.TIME_UNSET) {
                        updateUi { copy(duration = durMs / 1000f) }
                    }
                }
                if (state == Player.STATE_ENDED) {
                    processIntent(PlaySongIntent.Next)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateUi { copy(isPlaying = isPlaying) }
            }
        })

        startProgressUpdater()
    }

    private var progressJob: Job? = null
    private fun startProgressUpdater() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (true) {
                updateUi {
                    copy(seekBarPosition = exoPlayer.currentPosition / 1000f)
                }
                delay(500)
            }
        }
    }

    private fun updateUi(update: PlaySongUiState.() -> PlaySongUiState) {
        _uiState.value = _uiState.value.update()
    }

    override fun onCleared() {
        progressJob?.cancel()
        exoPlayer.release()
        super.onCleared()
    }

    data class PlaySongUiState(
        val currentSong: SongModel? = null,
        val isPlaying: Boolean = false,
        val seekBarPosition: Float = 0f,
        val duration: Float = 0f
    )
}