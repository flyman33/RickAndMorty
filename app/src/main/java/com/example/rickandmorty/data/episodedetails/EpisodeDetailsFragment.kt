package com.example.rickandmorty.data.episodedetails

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characterdetails.CharacterDetailsFragment
import com.example.rickandmorty.data.locationdetails.ResidentsAdapter
import com.example.rickandmorty.database.characters.Character
import com.example.rickandmorty.databinding.EpisodeDetailsFragmentBinding

class EpisodeDetailsFragment : Fragment() {

    companion object {

        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(id: Int) = EpisodeDetailsFragment().apply {
            arguments = Bundle().also {
                it.putInt(ID_EXTRA, id)
            }
        }
    }

    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var characterAdapter: ResidentsAdapter
    private lateinit var characterPositionList: MutableList<Int>
    private lateinit var binding: EpisodeDetailsFragmentBinding
    private var id: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        id = requireArguments().getInt(ID_EXTRA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.episode_details_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterList = mutableListOf<Character>()
        characterPositionList = mutableListOf()

        characterRecyclerView = view.findViewById(R.id.location_characters_recycler_view)

        initRecyclerView()

        val episodeDetailsViewModelFactory = EpisodeDetailsViewModelFactory(id!!)
        val viewModel = ViewModelProviders.of(this, episodeDetailsViewModelFactory).get(
            EpisodeDetailsViewModel::class.java
        )

        viewModel.episode.observe(viewLifecycleOwner, Observer { episode ->
            binding.episodeDetailName.text = episode.name
            binding.episodeDetailAirDate.text = episode.air_date
            binding.episode.text = episode.episode

            for (element in episode.characters) {
                val value = element.filter { it.isDigit() }.toInt()

                viewModel.characters(value).observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.character_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        characterList.add(it)
                        characterPositionList.add(value)

                        if (episode.characters.lastIndex == episode.characters.size - 1) {
                            characterAdapter.characters = characterList
                        }
                    }
                })
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initRecyclerView() {
        characterRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            characterAdapter = ResidentsAdapter()
            adapter = characterAdapter

            characterAdapter.onItemClick = { position ->

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        CharacterDetailsFragment.newInstance(characterPositionList[position])
                    ).addToBackStack(null).commit()
            }
        }
    }
}