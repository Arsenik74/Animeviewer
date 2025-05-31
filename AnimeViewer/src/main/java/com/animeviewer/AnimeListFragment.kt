package com.animeviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.animeviewer.databinding.FragmentAnimeListBinding

class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var siteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            siteId = it.getString(ARG_SITE_ID) ?: "anime-sama"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadAnimes()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = AnimeAdapter { anime ->
                // Navigation vers les détails de l'anime
                startActivity(AnimeDetailsActivity.newIntent(context, anime))
            }
        }
    }

    private fun loadAnimes() {
        // TODO: Implémenter le chargement des animes depuis l'API
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SITE_ID = "site_id"

        fun newInstance(siteId: String) = AnimeListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SITE_ID, siteId)
            }
        }
    }
} 