package com.example.rickandmorty.data.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import com.example.rickandmorty.repository.Repository

class CharacterDetailsViewModel(val id: Int) : ViewModel() {

    private val retrofitInstance =
        RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
    private val repository = Repository(retrofitInstance)

    val character: LiveData<Character> = repository.getCharacter(id)

    fun episodes(id: Int): LiveData<Episode> = repository.getEpisode(id)
}