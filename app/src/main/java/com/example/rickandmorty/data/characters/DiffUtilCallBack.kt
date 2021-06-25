package com.example.rickandmorty.data.characters

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.database.characters.Character

class DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name && oldItem.species == newItem.species &&
                oldItem.gender == newItem.gender
    }
}