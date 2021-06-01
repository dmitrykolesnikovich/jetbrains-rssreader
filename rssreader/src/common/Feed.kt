package rssreader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("description") val desc: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("posts") val posts: List<Post>,
    @SerialName("sourceUrl") val sourceUrl: String,
    @SerialName("isDefault") val isDefault: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Feed) return false
        return sourceUrl == other.sourceUrl
    }

    override fun hashCode(): Int = sourceUrl.hashCode()

}
