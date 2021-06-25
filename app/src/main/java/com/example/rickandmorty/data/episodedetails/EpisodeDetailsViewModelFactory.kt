package com.example.rickandmorty.data.episodedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EpisodeDetailsViewModelFactory(val id: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodeDetailsViewModel(id) as T
    }
}