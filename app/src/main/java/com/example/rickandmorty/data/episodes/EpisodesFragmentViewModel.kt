package com.example.rickandmorty.data.episodes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmorty.database.episodes.Episode
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class EpisodesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var episodesList: LiveData<PagedList<Episode>>? = null

    init {
        initPaging()
    }

    private fun initPaging() {
        val factory = EpisodesListDataSourceFactory()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        val executor: Executor = Executors.newFixedThreadPool(5)
        episodesList = LivePagedListBuilder<Int, Episode>(factory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun getRecyclerListObserver(): LiveData<PagedList<Episode>>? {
        return episodesList
    }
}