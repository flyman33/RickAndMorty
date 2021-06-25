package com.example.rickandmorty.data.locations

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.database.locations.Location
import com.example.rickandmorty.database.locations.LocationList
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationsListDataSource : PageKeyedDataSource<Int, Location>() {
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Location>) {

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllLocations(params.key)
            call.enqueue(object : Callback<LocationList> {

                override fun onResponse(
                    call: Call<LocationList>,
                    response: Response<LocationList>
                ) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.IO).launch {
                            MainActivity.db.locationDao().insertAll(response.body()?.results!!)
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            callback.onResult(response.body()?.results!!, params.key + 1)
                        }
                    }
                }

                override fun onFailure(call: Call<LocationList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Location>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Location>
    ) {
        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllLocations(1)
            call.enqueue(object : Callback<LocationList> {

                override fun onResponse(
                    call: Call<LocationList>,
                    response: Response<LocationList>
                ) {

                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.IO).launch {
                            MainActivity.db.locationDao().insertAll(response.body()?.results!!)
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            callback.onResult(response.body()?.results!!, null, 2)
                        }
                    }
                }

                override fun onFailure(call: Call<LocationList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        } else {
            callback.onResult(MainActivity.db.locationDao().getAllLocations(), null, 2)
        }
    }
}
