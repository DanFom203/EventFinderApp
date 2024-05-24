package com.itis.feature.events.api.domain.request

data class UsersFavouriteEventsRequest (
    val userId: String,
    val eventId: Int
)