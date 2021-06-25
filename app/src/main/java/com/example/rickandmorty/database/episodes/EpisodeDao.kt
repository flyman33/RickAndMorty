package com.example.rickandmorty.database.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes() : List<Episode>

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun getEpisode(id: Int): Episode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Episode>)
}