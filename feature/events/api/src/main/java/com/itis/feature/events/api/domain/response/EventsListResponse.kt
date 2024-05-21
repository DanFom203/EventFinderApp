package com.itis.feature.events.api.domain.response

import com.google.gson.annotations.SerializedName

data class EventsListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val events: List<Event>
)

data class Event(
    @SerializedName("id")
    val id: Int,
    @SerializedName("dates")
    val dates: List<Dates>,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<EventImage>,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int
)

data class Dates(
    @SerializedName("start")
    val startDate: Long,
    @SerializedName("end")
    val endDate: Long
)

data class EventImage(
    @SerializedName("image")
    val imageUrl: String
)