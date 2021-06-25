package com.example.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.database.locations.Location
import com.example.rickandmorty.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitService: RetrofitService
) {

    fun getCharacter(characterId: Int): LiveData<Character> {
        val data = MutableLiveData<Character>()

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            retrofitService.getCharacter(characterId).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                data.postValue(MainActivity.db.characterDao().getCharacter(characterId))
            }
        }

        return data
    }

    fun getLocation(locationId: Int): LiveData<Location> {
        val data = MutableLiveData<Location>()

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                retrofitService.getLocation(locationId).enqueue(object : Callback<Location> {
                    override fun onResponse(call: Call<Location>, response: Response<Location>) {
                        data.value = response.body()
                    }

                    override fun onFailure(call: Call<Location>, t: Throwable) {
                        Log.d("Error", t.toString())
                    }
                })
            }

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                data.postValue(MainActivity.db.locationDao().getLocation(locationId))
            }
        }

        return data
    }

    fun getEpisode(episodeId: Int): LiveData<Episode> {
        val data = MutableLiveData<Episode>()

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            retrofitService.getEpisode(episodeId).enqueue(object : Callback<Episode> {
                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                data.postValue(MainActivity.db.episodeDao().getEpisode(episodeId))
            }
        }

        return data
    }
}