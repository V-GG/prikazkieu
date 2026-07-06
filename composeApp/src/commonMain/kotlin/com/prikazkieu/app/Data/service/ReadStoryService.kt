package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import prikazkieu.composeapp.generated.resources.Res

class ReadStoryService(
    private val client: HttpClient = HttpClient(),
    override val dataService: IDataService
) : IReadStoryService {

    override suspend fun getStoryContentPaginated(url: String): List<HtmlPage> {
        val html = client.get(url).bodyAsText()
        val template = Res.readBytes("files/storySinglePageTemplate.html").decodeToString()

        val title = extractTitle(html)
        val article = extractArticleHtml(html)

        val result = mutableListOf<HtmlPage>()

        // TODO: Implement algorithm desegmenting the story content into different pages to fit in screen when rendered


        return result
    }

    override suspend fun getStorySuggestionBy(story: Story): List<StorySuggestion> {
        val results = mutableListOf<StorySuggestion>()

        story.author?.let {
            for (result in dataService.getStoriesByAuthor(it)) {
                results.add(StorySuggestion.AuthorSuggestion(result))
            }
        }

        story.kingdom?.let {
            for (result in dataService.getStoriesByKingdom(it)) {
                results.add(StorySuggestion.KingdomSuggestion(result))
            }
        }

        return results
    }

    private fun extractTitle(html: String): String? =
        Regex("<title>(.*?)</title>", RegexOption.IGNORE_CASE)
            .find(html)
            ?.groupValues?.get(1)
            ?.trim()

    private fun extractArticleHtml(html: String): String? =
        Regex("<article (.*?)</article>", RegexOption.IGNORE_CASE)
            .find(html)
            ?.groupValues?.get(1)
            ?.trim()
}