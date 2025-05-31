package com.animeviewer.parser

import com.animeviewer.model.Anime
import com.animeviewer.model.Episode
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import javax.inject.Inject

class VoirAnimeParser @Inject constructor() : AnimeParser {

    override suspend fun parseAnimeList(html: String): List<Anime> {
        val doc = Jsoup.parse(html)
        return doc.select("div.anime-item").map { element ->
            parseAnimeItem(element)
        }
    }

    override suspend fun parseAnimeDetails(html: String): Anime {
        val doc = Jsoup.parse(html)
        val title = doc.select("h1.title").text()
        val description = doc.select("div.synopsis").text()
        val imageUrl = doc.select("div.poster img").attr("src")
        val genres = doc.select("div.genres a").map { it.text() }
        val rating = doc.select("div.rating").text().toFloatOrNull() ?: 0f
        val episodes = parseEpisodes(html)

        return Anime(
            id = extractAnimeId(doc),
            title = title,
            description = description,
            imageUrl = imageUrl,
            episodes = episodes,
            genres = genres,
            rating = rating,
            siteId = "voiranime"
        )
    }

    override suspend fun parseEpisodes(html: String): List<Episode> {
        val doc = Jsoup.parse(html)
        return doc.select("div.episode").map { element ->
            val number = element.select("span.number").text().toIntOrNull() ?: 0
            val title = element.select("span.title").text()
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
        return doc.select("iframe#player").attr("src")
    }

    private fun parseAnimeItem(element: Element): Anime {
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
            siteId = "voiranime"
        )
    }

    private fun extractAnimeId(doc: Document): String {
        return doc.select("meta[property=og:url]").attr("content").split("/").last()
    }
} 