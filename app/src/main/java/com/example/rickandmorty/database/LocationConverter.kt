package com.example.rickandmorty.database

import androidx.room.TypeConverter
import com.example.rickandmorty.database.characters.Location
import com.google.gson.Gson

class LocationConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Location {
        return Gson().fromJson(value, Location::class.java)
    }

    @TypeConverter
    fun fromObject(value: Location): String {
        return gson.toJson(value)
    }
}