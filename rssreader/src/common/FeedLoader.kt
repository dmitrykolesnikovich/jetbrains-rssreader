package rssreader

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

internal class FeedLoader(private val httpClient: HttpClient, private val parser: FeedParser) {

    suspend fun getFeed(url: String, isDefault: Boolean): Feed {
        val xml = httpClient.get<HttpResponse>(url).readText()
        return parser.parse(url, xml, isDefault)
    }

}
