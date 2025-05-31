package com.animeviewer.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animeviewer.model.Episode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : ComponentActivity() {
    companion object {
        const val EXTRA_EPISODE = "extra_episode"
        const val EXTRA_SITE_ID = "extra_site_id"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val episode = intent.getParcelableExtra<Episode>(EXTRA_EPISODE)
        val siteId = intent.getStringExtra(EXTRA_SITE_ID) ?: ""

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayerScreen(
                        episode = episode,
                        siteId = siteId,
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerScreen(
    episode: Episode?,
    siteId: String,
    onBackPressed: () -> Unit
) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barre d'outils
        TopAppBar(
            title = { Text(episode?.title ?: "") },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                }
            }
        )

        // WebView pour la lecture
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                    }
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                    webView = this
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { view ->
            episode?.let {
                when (siteId) {
                    "voiranime" -> {
                        // Pour VoirAnime, on charge directement la page de l'épisode
                        view.loadUrl(it.videoUrl)
                    }
                    else -> {
                        // Pour les autres sites, on charge directement l'URL de la vidéo
                        view.loadUrl(it.videoUrl)
                    }
                }
            }
        }

        // Indicateur de chargement
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
} 