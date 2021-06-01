package rssreader

fun FeedStore.watchState() = observeState().wrap()
fun FeedStore.watchSideEffect() = observeSideEffect().wrap()
