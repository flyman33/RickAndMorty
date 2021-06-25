package com.example.rickandmorty.data.characterdetails

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
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.episodedetails.EpisodeDetailsFragment
import com.example.rickandmorty.database.episodes.Episode
import com.example.rickandmorty.databinding.CharacterDetailsFragmentBinding

class CharacterDetailsFragment : Fragment() {

    companion object {

        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(id: Int) = CharacterDetailsFragment().apply {
            arguments = Bundle().also {
                it.putInt(ID_EXTRA, id)
            }
        }
    }

    private lateinit var episodesRecyclerView: RecyclerView
    private lateinit var episodeAdapter: CharacterEpisodesAdapter
    private lateinit var episodePositionList: MutableList<Int>
    private lateinit var binding: CharacterDetailsFragmentBinding
    private var id: Int? = null
    lateinit var clickLocation: OnClick

    override fun onAttach(context: Context) {
        super.onAttach(context)

        id = requireArguments().getInt(ID_EXTRA)
        clickLocation = context as OnClick
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.character_details_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val episodeList = mutableListOf<Episode>()
        episodePositionList = mutableListOf()
        episodesRecyclerView = view.findViewById(R.id.characters_episodes_recycler_view)

        initRecyclerView()

        val characterDetailsViewModelFactory = CharacterDetailsViewModelFactory(id!!)
        val viewModel = ViewModelProviders.of(this, characterDetailsViewModelFactory)
            .get(CharacterDetailsViewModel::class.java)

        viewModel.character.observe(viewLifecycleOwner, Observer { character ->
            binding.characterDetailName.text = character.name
            binding.characterDetailStatus.text = character.status
            binding.characterDetailSpecies.text = character.species
            binding.characterDetailGender.text = character.gender
            binding.characterOrigin.text = character.origin.name
            binding.characterLocation.text = character.location.name
            Glide.with(requireContext()).load(character.image).centerCrop()
                .into(binding.characterDetailImage)

            for (element in character.episode) {
                val value = element.filter { it.isDigit() }.toInt()

                viewModel.episodes(value).observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.episodes_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        episodeList.add(it)
                        episodePositionList.add(value)

                        if (character.episode.lastIndex == character.episode.size - 1) {
                            episodeAdapter.episodes = episodeList
                        }
                    }
                })
            }

            binding.characterOrigin.setOnClickListener {
                if (character.origin.url != "") {
                    val location = character.origin.url.filter { it.isDigit() }.toInt()
                    clickLocation.onClickLocation(location)
                }
            }

            binding.characterLocation.setOnClickListener {
                if (character.location.url != "") {
                    val location = character.location.url.filter { it.isDigit() }.toInt()
                    clickLocation.onClickLocation(location)
                }
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
        episodesRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            episodeAdapter = CharacterEpisodesAdapter()
            adapter = episodeAdapter

            episodeAdapter.onItemClick = { position ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        EpisodeDetailsFragment.newInstance(episodePositionList[position])
                    ).addToBackStack(null).commit()
            }
        }
    }

    interface OnClick {
        fun onClickLocation(id: Int)
    }
}