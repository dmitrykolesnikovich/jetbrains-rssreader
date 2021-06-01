package rssreader

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier
import com.russhwolf.settings.AndroidSettings
import kotlinx.serialization.json.Json

@RequiresApi(Build.VERSION_CODES.O)
fun RssReader.Companion.create(ctx: Context, withLog: Boolean) = RssReader(
    FeedLoader(AndroidHttpClient(withLog), AndroidFeedParser()),
    FeedStorage(
        AndroidSettings(ctx.getSharedPreferences("rss_reader_pref", Context.MODE_PRIVATE)),
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    )
).also {
    if (withLog) Napier.base(DebugAntilog())
}
