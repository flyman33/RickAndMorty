package com.example.rickandmorty.data.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.database.locations.Location

class LocationsAdapter :
    PagedListAdapter<Location, LocationsAdapter.LocationViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (Int) -> Unit = {}

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.location_name)
        private val type = itemView.findViewById<TextView>(R.id.location_type)
        private val dimension = itemView.findViewById<TextView>(R.id.location_dimension)

        fun bind(location: Location) {
            name.text = location.name
            type.text = location.type
            dimension.text = location.dimension
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.itemView.setOnClickListener { onItemClick(holder.absoluteAdapterPosition) }
    }
}