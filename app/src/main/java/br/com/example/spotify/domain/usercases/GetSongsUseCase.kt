package br.com.example.spotify.domain.usercases

import br.com.example.spotify.domain.model.Song
import br.com.example.spotify.domain.repository.MusicRepository
import javax.inject.Inject


class GetSongsUseCase @Inject constructor(private val repository: MusicRepository) {
    suspend operator fun invoke(): List<Song> = repository.getSongs()
}