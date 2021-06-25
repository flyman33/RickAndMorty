package com.example.rickandmorty.data.characters

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characterdetails.CharacterDetailsFragment
import com.example.rickandmorty.database.characters.Character

class CharactersFragment : Fragment() {

    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var characterAdapter: CharactersAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.characters_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwipeRefreshLayout = view.findViewById(R.id.characters_swipe_refresh_layout)
        characterRecyclerView = view.findViewById(R.id.characters_recycler_view)

        initRecyclerView()
        initViewModel()

        mSwipeRefreshLayout.setOnRefreshListener() {
            characterRecyclerView.apply {
                layoutManager = null
                adapter = null
                characterAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = characterAdapter
            }
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        characterRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            characterAdapter = CharactersAdapter()
            adapter = characterAdapter

            characterAdapter.onItemClick = { position ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        CharacterDetailsFragment.newInstance(position + 1)
                    ).addToBackStack(null).commit()
            }
        }
    }

    private fun initViewModel() {

        val viewModel = ViewModelProvider(this).get(CharactersFragmentViewModel::class.java)

        viewModel.getRecyclerListObserver()
            ?.observe(viewLifecycleOwner, Observer<PagedList<Character>> {
                if (it != null) {
                    characterAdapter.submitList(it)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.downloading_data_issue),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}