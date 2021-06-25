package com.example.rickandmorty.data.characters

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.characters.CharacterList
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.network.RetrofitService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersListDataSource : PageKeyedDataSource<Int, Character>() {

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllCharacters(params.key)
            call.enqueue(object : Callback<CharacterList> {

                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            MainActivity.db.characterDao().insertAll(response.body()?.results!!)
                        }
                        GlobalScope.launch {
                            callback.onResult(response.body()?.results!!, params.key + 1)
                        }
                    }
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {

        if (MainActivity.connectivityManager.activeNetworkInfo != null &&
            MainActivity.connectivityManager.activeNetworkInfo!!.isConnected
        ) {

            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val call = retrofitInstance.getAllCharacters(1)
            call.enqueue(object : Callback<CharacterList> {

                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {

                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            MainActivity.db.characterDao().insertAll(response.body()?.results!!)
                        }
                        GlobalScope.launch {
                            callback.onResult(response.body()?.results!!, null, 2)
                        }
                    }
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    Log.d("Error", t.toString())
                }
            })
        } else {
            callback.onResult(MainActivity.db.characterDao().getAllCharacters(), null, 2)
        }
    }
}