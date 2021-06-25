package com.example.rickandmorty.data.locations

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.database.locations.Location

class DiffUtilCallBack : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.name == newItem.name && oldItem.type == newItem.type &&
                oldItem.dimension == newItem.dimension
    }
}