package com.example.rickandmorty.data.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmorty.database.characters.Character
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CharactersFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var charactersList: LiveData<PagedList<Character>>? = null

    init {
        initPaging()
    }

    private fun initPaging() {
        val factory = CharactersListDataSourceFactory()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        val executor: Executor = Executors.newFixedThreadPool(5)
        charactersList = LivePagedListBuilder<Int, Character>(factory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun getRecyclerListObserver(): LiveData<PagedList<Character>>? {
        return charactersList
    }
}