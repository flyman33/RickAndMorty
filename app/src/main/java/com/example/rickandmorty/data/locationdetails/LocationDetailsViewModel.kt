package com.example.rickandmorty.data.locationdetails

import androidx.lifecycle.*
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.locations.Location
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import com.example.rickandmorty.repository.Repository

class LocationDetailsViewModel(val id: Int) : ViewModel() {

    private val retrofitInstance =
        RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
    private val repository = Repository(retrofitInstance)

    val location: LiveData<Location> = repository.getLocation(id)

    fun residents(id: Int): LiveData<Character> = repository.getCharacter(id)
}