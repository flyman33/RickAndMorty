package com.example.rickandmorty.database.locations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    fun getAllLocations() : List<Location>

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocation(id: Int): Location

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Location>)
}