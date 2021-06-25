package com.example.rickandmorty.data.characters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rickandmorty.database.characters.Character

class CharactersListDataSourceFactory() : DataSource.Factory<Int, Character>() {

    private var characterLiveData: MutableLiveData<CharactersListDataSource>? = null

    init {
        characterLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Character> {
        val listDataSource = CharactersListDataSource()
        characterLiveData?.postValue(listDataSource)
        return listDataSource
    }
}