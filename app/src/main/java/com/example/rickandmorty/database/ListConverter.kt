package com.example.rickandmorty.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object:TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return gson.toJson(value)
    }
}