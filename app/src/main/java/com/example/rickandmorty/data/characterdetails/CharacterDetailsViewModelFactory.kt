package com.example.rickandmorty.data.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharacterDetailsViewModelFactory(val id: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return CharacterDetailsViewModel(id) as T
    }
}