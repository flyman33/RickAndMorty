package com.example.rickandmorty.data.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.database.characters.Character

class CharactersAdapter : PagedListAdapter<Character, CharactersAdapter.CharacterViewHolder>(
    DiffUtilCallBack()
) {

    var onItemClick: (Int) -> Unit = {}

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.character_name)
        private val species = itemView.findViewById<TextView>(R.id.character_species)
        private val status = itemView.findViewById<TextView>(R.id.character_status)
        private val gender = itemView.findViewById<TextView>(R.id.character_gender)
        private val image = itemView.findViewById<ImageView>(R.id.character_image)

        fun bind(character: Character) {
            name.text = character.name
            species.text = character.species
            status.text = character.status
            gender.text = character.gender
            Glide.with(itemView.context).load(character.image).centerCrop().into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.itemView.setOnClickListener { onItemClick(holder.absoluteAdapterPosition) }
    }
}