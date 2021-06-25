package com.example.rickandmorty.data.episodes

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.database.episodes.EpisodeList
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodesListDataSource : PageKeyedDataSource<Int, Episode>() {
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllEpisodes(params.key)
            call.enqueue(object : Callback<EpisodeList> {

                override fun onResponse(
                    call: Call<EpisodeList>,
                    response: Response<EpisodeList>
                ) {
                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            MainActivity.db.episodeDao().insertAll(response.body()?.results!!)
                        }
                        GlobalScope.launch {
                            callback.onResult(response.body()?.results!!, params.key + 1)
                        }
                    }
                }

                override fun onFailure(call: Call<EpisodeList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Episode>
    ) {
        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllEpisodes(1)
            call.enqueue(object : Callback<EpisodeList> {

                override fun onResponse(
                    call: Call<EpisodeList>,
                    response: Response<EpisodeList>
                ) {

                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            MainActivity.db.episodeDao().insertAll(response.body()?.results!!)
                        }
                        GlobalScope.launch {
                            callback.onResult(response.body()?.results!!, null, 2)
                        }
                    }
                }

                override fun onFailure(call: Call<EpisodeList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        } else {
            callback.onResult(MainActivity.db.episodeDao().getAllEpisodes(), null, 2)
        }
    }
}