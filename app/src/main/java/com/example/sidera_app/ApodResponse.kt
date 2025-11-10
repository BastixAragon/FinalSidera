package com.example.sidera_app

import com.google.gson.annotations.SerializedName

data class ApodResponse(
    val title: String,
    val date: String,
    val explanation: String,
    val url: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?
) {
    val displayUrl: String?
        get() = when (mediaType) {
            "image" -> url
            "video" -> thumbnailUrl ?: url
            else -> url
        }
}
