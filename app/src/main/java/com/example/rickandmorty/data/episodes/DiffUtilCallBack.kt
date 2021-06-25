package com.example.rickandmorty.data.episodes

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.database.episodes.Episode

class DiffUtilCallBack : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.name == newItem.name && oldItem.air_date == newItem.air_date &&
                oldItem.episode == newItem.episode
    }
}