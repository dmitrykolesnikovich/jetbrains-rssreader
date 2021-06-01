package rssreader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("title") val title: String,
    @SerialName("link") val link: String?,
    @SerialName("description") val desc: String?,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("date") val date: Long
)