package com.example.rickandmorty.data.locations

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rickandmorty.database.locations.Location

class LocationsListDataSourceFactory : DataSource.Factory<Int, Location>() {

    private var locationLiveData: MutableLiveData<LocationsListDataSource>? = null

    init {
        locationLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Location> {
        val listDataSource = LocationsListDataSource()
        locationLiveData?.postValue(listDataSource)
        return listDataSource
    }
}