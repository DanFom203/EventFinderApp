package com.itis.feature.events.impl.presentation.model

data class EventsListUiModel(
    val count: Int,
    val events: List<EventUiModel>
)

data class EventUiModel(
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