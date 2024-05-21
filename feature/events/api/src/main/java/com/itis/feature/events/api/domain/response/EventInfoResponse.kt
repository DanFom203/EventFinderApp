package com.itis.feature.events.api.domain.response

import com.google.gson.annotations.SerializedName

data class EventInfoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: Place,
    @SerializedName("description")
    val description: String,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("age_restriction")
    val ageRestriction: String? = null,
    @SerializedName("is_free")
    val isFree: Boolean,
    @SerializedName("images")
    val images: List<EventImage>,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int
)

data class Place(
    @SerializedName("address")
    val address: String,
    @SerializedName("location")
    val location: String
)