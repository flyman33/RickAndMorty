package com.example.rickandmorty.data.locations

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
import com.example.rickandmorty.data.locationdetails.LocationDetailsFragment
import com.example.rickandmorty.database.locations.Location

class LocationsFragment : Fragment() {

    private lateinit var locationRecyclerView: RecyclerView
    private lateinit var locationAdapter: LocationsAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.locations_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwipeRefreshLayout = view.findViewById(R.id.locations_swipe_refresh_layout)
        locationRecyclerView = view.findViewById(R.id.locations_recycler_view)

        initRecyclerView()
        initViewModel()

        mSwipeRefreshLayout.setOnRefreshListener() {
            locationRecyclerView.apply {
                layoutManager = null
                adapter = null
                locationAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = locationAdapter
            }
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        locationRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            locationAdapter = LocationsAdapter()
            adapter = locationAdapter

            locationAdapter.onItemClick = { position ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        LocationDetailsFragment.newInstance(position + 1)
                    ).addToBackStack(null).commit()
            }
        }
    }

    private fun initViewModel() {

        val viewModel = ViewModelProvider(this).get(LocationsFragmentViewModel::class.java)

        viewModel.getRecyclerListObserver()
            ?.observe(viewLifecycleOwner, Observer<PagedList<Location>> {
                if (it != null) {
                    locationAdapter.submitList(it)
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