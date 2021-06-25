package com.example.rickandmorty.database.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character (
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    val episode: List<String>,
    val origin: Origin,
    val location: Location
)