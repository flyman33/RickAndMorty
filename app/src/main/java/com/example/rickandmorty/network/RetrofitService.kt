package com.example.rickandmorty.network

import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.characters.CharacterList
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.database.episodes.EpisodeList
import com.example.rickandmorty.database.locations.Location
import com.example.rickandmorty.database.locations.LocationList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/character")
    fun getAllCharacters(@Query("page") page: Int): Call<CharacterList>

    @GET("api/character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<Character>

    @GET("api/location")
    fun getAllLocations(@Query("page") page: Int): Call<LocationList>

    @GET("api/location/{id}")
    fun getLocation(@Path("id") id: Int): Call<Location>

    @GET("api/episode")
    fun getAllEpisodes(@Query("page") page: Int): Call<EpisodeList>

    @GET("api/episode/{id}")
    fun getEpisode(@Path("id") id: Int): Call<Episode>
}