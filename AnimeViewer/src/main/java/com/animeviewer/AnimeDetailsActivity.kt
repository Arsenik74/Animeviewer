package com.animeviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.animeviewer.adapter.EpisodeAdapter
import com.animeviewer.databinding.ActivityAnimeDetailsBinding
import com.animeviewer.model.Anime
import com.animeviewer.viewmodel.AnimeDetailsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeDetailsBinding
    private val viewModel: AnimeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getStringExtra(EXTRA_ANIME_ID) ?: return
        val siteId = intent.getStringExtra(EXTRA_SITE_ID) ?: return

        setupUI()
        viewModel.loadAnimeDetails(siteId, animeId)
        observeViewModel()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.episodesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is AnimeDetailsViewModel.UiState.Loading -> {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                    binding.contentLayout.visibility = android.view.View.GONE
                }
                is AnimeDetailsViewModel.UiState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.contentLayout.visibility = android.view.View.VISIBLE
                    updateUI(state.anime)
                }
                is AnimeDetailsViewModel.UiState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    // TODO: Afficher l'erreur
                }
            }
        }
    }

    private fun updateUI(anime: Anime) {
        binding.toolbar.title = anime.title
        binding.descriptionTextView.text = anime.description
        binding.genresTextView.text = anime.genres.joinToString(", ")
        binding.ratingTextView.text = anime.rating.toString()

        Glide.with(this)
            .load(anime.imageUrl)
            .centerCrop()
            .into(binding.imageView)

        binding.episodesRecyclerView.adapter = EpisodeAdapter(anime.episodes) { episode ->
            startActivity(VideoPlayerActivity.newIntent(this, episode.videoUrl))
        }
    }

    companion object {
        private const val EXTRA_ANIME_ID = "extra_anime_id"
        private const val EXTRA_SITE_ID = "extra_site_id"

        fun newIntent(context: Context, anime: Anime): Intent {
            return Intent(context, AnimeDetailsActivity::class.java).apply {
                putExtra(EXTRA_ANIME_ID, anime.id)
                putExtra(EXTRA_SITE_ID, anime.siteId)
            }
        }
    }
} 