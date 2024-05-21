package com.itis.feature.events.impl.presentation.model

import com.itis.feature.events.api.domain.response.EventImage

data class EventInfoUiModel(
    val id: Int,
    val title: String,
    val address: String,
    val location: String,
    val description: String,
    val categories: List<String>,
    val ageRestriction: String,
    val isFree: Boolean,
    val images: List<EventImage>,
    val favoritesCount: Int,
    val commentsCount: Int
)