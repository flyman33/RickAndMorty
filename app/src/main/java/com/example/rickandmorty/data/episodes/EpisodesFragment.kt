package com.example.rickandmorty.data.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.rickandmorty.data.episodedetails.EpisodeDetailsFragment
import com.example.rickandmorty.database.episodes.Episode

class EpisodesFragment : Fragment() {

    private lateinit var episodeRecyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodesAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.episodes_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwipeRefreshLayout = view.findViewById(R.id.episodes_swipe_refresh_layout)
        episodeRecyclerView = view.findViewById(R.id.episodes_recycler_view)

        initRecyclerView()
        initViewModel()

        mSwipeRefreshLayout.setOnRefreshListener() {
            episodeRecyclerView.apply {
                layoutManager = null
                adapter = null
                episodeAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = episodeAdapter
            }
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        episodeRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            episodeAdapter = EpisodesAdapter()
            adapter = episodeAdapter

            episodeAdapter.onItemClick = { position ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        EpisodeDetailsFragment.newInstance(position + 1)
                    ).addToBackStack(null).commit()
            }
        }
    }

    private fun initViewModel() {

        val viewModel = ViewModelProvider(this).get(EpisodesFragmentViewModel::class.java)

        viewModel.getRecyclerListObserver()
            ?.observe(viewLifecycleOwner, Observer<PagedList<Episode>> {
                if (it != null) {
                    episodeAdapter.submitList(it)
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