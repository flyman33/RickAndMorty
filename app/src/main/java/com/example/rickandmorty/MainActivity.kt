package com.example.rickandmorty

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rickandmorty.data.characterdetails.CharacterDetailsFragment
import com.example.rickandmorty.data.characters.CharactersFragment
import com.example.rickandmorty.data.episodes.EpisodesFragment
import com.example.rickandmorty.data.locationdetails.LocationDetailsFragment
import com.example.rickandmorty.data.locations.LocationsFragment
import com.example.rickandmorty.database.RickAndMortyDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), CharacterDetailsFragment.OnClick {

    companion object {
        lateinit var db: RickAndMortyDatabase
        lateinit var connectivityManager: ConnectivityManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = RickAndMortyDatabase.getDatabase(applicationContext)
        connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharactersFragment()).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.action_characters -> {
                selectedFragment = CharactersFragment()
            }
            R.id.action_locations -> {
                selectedFragment = LocationsFragment()
            }
            R.id.action_episodes -> {
                selectedFragment = EpisodesFragment()
            }
        }

        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment
            ).commit()
        }
        true
    }

    override fun onClickLocation(id: Int) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            LocationDetailsFragment.newInstance(id)
        ).addToBackStack(null).commit()
    }
}