package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion
import com.prikazkieu.app.ui.navigation.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import prikazkieu.composeapp.generated.resources.Res

class ReadStoryService(
    private val client: HttpClient = HttpClient(),
    override val dataService: IDataService
) : IReadStoryService {

    override suspend fun getContentFor(story: Story): HtmlPage {

        val url = story.url
        val html = client.get("$BASE_URL/$url").bodyAsText()
        var content = Res.readBytes("files/storySinglePageTemplate.html").decodeToString()

        val head = extractHeadHtml(html)
        val article = extractArticleHtml(html)

        head?.let {
            content = insertHeadHtmlInto(content, it)
        }

        article?.let {
            content = insertArticleHtmlInto(content, it)
        }

        return HtmlPage(story.url, story.title, content)
    }

    override suspend fun getSuggestionBy(story: Story): List<StorySuggestion> {
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

    private fun extractHeadHtml(html: String): String? =
        Regex("<head[^>]*>([\\s\\S]*?)<\\/head>", RegexOption.IGNORE_CASE)
            .find(html)
            ?.groupValues?.get(1)
            ?.trim()

    private fun extractArticleHtml(html: String): String? =
        Regex("""<main id="main">\s*(<article id="post-\d+"[^>]*>[\s\S]*?</article>)""")
            .find(html)
            ?.groupValues?.get(1)
            ?.trim()

    private fun insertHeadHtmlInto(template: String, head: String): String =
        Regex("<head><!-- ===Implement Head=== --></head>", option = RegexOption.IGNORE_CASE)
            .replace(template, head)

    private fun insertArticleHtmlInto(template: String, article: String): String =
        Regex("<article><!-- ===Implement=== --></article>", option = RegexOption.IGNORE_CASE)
            .replace(template, article)
}