package com.itis.feature.events.impl.domain.model

data class EventsListDomainModel(
    val count: Int,
    val events: List<EventDomainModel>
)

data class EventDomainModel(
    val id: Int,
    val startDate: Long,
    val endDate: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val favoritesCount: Int,
    val commentsCount: Int
)