package com.itis.feature.events.impl.domain.model

data class EventsListDomainModel(
    val count: Int,
    val events: List<EventDomainModel>
)

data class EventDomainModel(
    val id: String,
    val startDate: Long,
    val endDate: Long,
    val title: String,
    val description: String,
    val ageRestriction: String,
    val isFree: Boolean,
    val imageUrl: String,
    val favoritesCount: Int,
    val commentsCount: Int
)