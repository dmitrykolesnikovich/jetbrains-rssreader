import FeedStore
import com.github.jetbrains.rssreader.core.wrap

fun FeedStore.watchState() = observeState().wrap()
fun FeedStore.watchSideEffect() = observeSideEffect().wrap()