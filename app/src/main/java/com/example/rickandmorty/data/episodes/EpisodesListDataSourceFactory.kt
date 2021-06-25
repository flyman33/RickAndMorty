package com.example.rickandmorty.data.episodes

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rickandmorty.database.episodes.Episode

class EpisodesListDataSourceFactory : DataSource.Factory<Int, Episode>() {

    private var episodeLiveData: MutableLiveData<EpisodesListDataSource>? = null

    init {
        episodeLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Episode> {
        val listDataSource = EpisodesListDataSource()
        episodeLiveData?.postValue(listDataSource)
        return listDataSource
    }
}