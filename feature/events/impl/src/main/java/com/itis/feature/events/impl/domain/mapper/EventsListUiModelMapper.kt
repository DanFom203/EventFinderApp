package com.itis.feature.events.impl.domain.mapper

import com.itis.feature.events.impl.domain.model.EventsListDomainModel
import com.itis.feature.events.impl.presentation.model.EventUiModel
import com.itis.feature.events.impl.presentation.model.EventsListUiModel
import javax.inject.Inject

class EventsListUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: EventsListDomainModel): EventsListUiModel {
        val eventsList = mutableListOf<EventUiModel>()
        input.let {
            it.events.forEach { eventData ->
                val event = EventUiModel(
                    id = eventData.id,
                    startDate = eventData.startDate,
                    endDate = eventData.endDate,
                    title = eventData.title,
                    description = eventData.description,
                    imageUrl = eventData.imageUrl,
                    favoritesCount = eventData.favoritesCount,
                    commentsCount = eventData.commentsCount
                )
                eventsList.add(event)
            }
            return EventsListUiModel(
                count = it.count,
                events = eventsList
            )
        }
    }
}