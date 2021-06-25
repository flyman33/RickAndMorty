package com.example.rickandmorty.data.locationdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationDetailsViewModelFactory(val id: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return LocationDetailsViewModel(id) as T
    }
}