package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.database.characters.CharacterDao
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.database.episodes.EpisodeDao
import com.example.rickandmorty.database.locations.Location
import com.example.rickandmorty.database.locations.LocationDao

@Database(entities = [Character::class, Location::class, Episode::class], version = 15)
@TypeConverters(ListConverter::class, OriginConverter::class, LocationConverter::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao

    companion object {

        @Volatile
        private var INSTANCE: RickAndMortyDatabase? = null

        fun getDatabase(context: Context): RickAndMortyDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickAndMortyDatabase::class.java,
                    "rick_and_morty_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}