package com.animeviewer.parser

import com.animeviewer.model.Anime
import com.animeviewer.model.Episode
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import javax.inject.Inject

class FranimeParser @Inject constructor() : AnimeParser {

    override suspend fun parseAnimeList(html: String): List<Anime> {
        val doc = Jsoup.parse(html)
        return doc.select("div.anime-card").map { element ->
            parseAnimeCard(element)
        }
    }

    override suspend fun parseAnimeDetails(html: String): Anime {
        val doc = Jsoup.parse(html)
        val title = doc.select("h1.anime-title").text()
        val description = doc.select("div.description").text()
        val imageUrl = doc.select("div.cover img").attr("src")
        val genres = doc.select("div.tags span").map { it.text() }
        val rating = doc.select("div.score").text().toFloatOrNull() ?: 0f
        val episodes = parseEpisodes(html)

        return Anime(
            id = extractAnimeId(doc),
            title = title,
            description = description,
            imageUrl = imageUrl,
            episodes = episodes,
            genres = genres,
            rating = rating,
            siteId = "franime"
        )
    }

    override suspend fun parseEpisodes(html: String): List<Episode> {
        val doc = Jsoup.parse(html)
        return doc.select("div.episode-card").map { element ->
            val number = element.select("span.episode-num").text().toIntOrNull() ?: 0
            val title = element.select("span.episode-name").text()
            val thumbnailUrl = element.select("img").attr("src")
            val videoUrl = element.select("a").attr("href")

            Episode(
                number = number,
                title = title,
                videoUrl = videoUrl,
                thumbnailUrl = thumbnailUrl
            )
        }
    }

    override suspend fun parseVideoUrl(html: String): String {
        val doc = Jsoup.parse(html)
        return doc.select("video#player source").attr("src")
    }

    private fun parseAnimeCard(element: Element): Anime {
        val title = element.select("h3.title").text()
        val imageUrl = element.select("img").attr("src")
        val id = element.select("a").attr("href").split("/").last()

        return Anime(
            id = id,
            title = title,
            description = "",
            imageUrl = imageUrl,
            episodes = emptyList(),
            genres = emptyList(),
            rating = 0f,
            siteId = "franime"
        )
    }

    private fun extractAnimeId(doc: Document): String {
        return doc.select("meta[property=og:url]").attr("content").split("/").last()
    }
} 