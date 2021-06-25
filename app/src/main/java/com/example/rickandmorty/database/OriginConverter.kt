package com.example.rickandmorty.database

import androidx.room.TypeConverter
import com.example.rickandmorty.database.characters.Origin
import com.google.gson.Gson

class OriginConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Origin {
        return Gson().fromJson(value, Origin::class.java)
    }

    @TypeConverter
    fun fromObject(value: Origin): String {
        return gson.toJson(value)
    }
}