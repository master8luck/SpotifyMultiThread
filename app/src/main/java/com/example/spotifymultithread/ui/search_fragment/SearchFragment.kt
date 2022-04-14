package com.example.spotifymultithread.ui.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.spotifymultithread.databinding.FragmentSearchBinding
import com.example.spotifymultithread.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    private val adapter = TrackAdapter(listOf(), this::onTrackClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tracks
            .observe(viewLifecycleOwner) { tracks ->
                adapter.setItems(tracks)
            }

        binding.run {
            rvTracks.adapter = adapter
            tilSearch.setEndIconOnClickListener {
                fetch(tilSearch.editText!!.text.toString())
            }
        }

    }

    private fun fetch(query: String) {
        viewModel.search(query)
    }

    private fun onTrackClick(href: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                href
            )
        )
    }

}