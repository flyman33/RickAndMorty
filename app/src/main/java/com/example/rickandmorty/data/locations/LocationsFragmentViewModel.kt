package com.example.rickandmorty.data.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmorty.database.locations.Location
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LocationsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var locationsList: LiveData<PagedList<Location>>? = null

    init {
        initPaging()
    }

    private fun initPaging() {
        val factory = LocationsListDataSourceFactory()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        val executor: Executor = Executors.newFixedThreadPool(5)
        locationsList = LivePagedListBuilder<Int, Location>(factory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun getRecyclerListObserver(): LiveData<PagedList<Location>>? {
        return locationsList
    }
}