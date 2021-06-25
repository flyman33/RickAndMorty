package com.example.rickandmorty.data.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.database.episodes.Episode

class EpisodesAdapter :
    PagedListAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (Int) -> Unit = {}

    class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.episode_name)
        private val airDate = itemView.findViewById<TextView>(R.id.episode_air_date)
        private val episodeText = itemView.findViewById<TextView>(R.id.episode)

        fun bind(episode: Episode) {
            name.text = episode.name
            airDate.text = episode.air_date
            episodeText.text = episode.episode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.itemView.setOnClickListener { onItemClick(holder.absoluteAdapterPosition) }
    }
}