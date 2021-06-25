package com.example.rickandmorty.database.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : List<Character>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Character>)
}   