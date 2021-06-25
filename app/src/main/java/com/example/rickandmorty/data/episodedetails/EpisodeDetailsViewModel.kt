package com.example.rickandmorty.data.episodedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import com.example.rickandmorty.repository.Repository

class EpisodeDetailsViewModel(val id: Int) : ViewModel() {

    private val retrofitInstance =
        RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
    private val repository = Repository(retrofitInstance)

    val episode: LiveData<Episode> = repository.getEpisode(id)

    fun characters(id: Int): LiveData<Character> = repository.getCharacter(id)
}