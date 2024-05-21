package com.itis.feature.events.impl.data.mapper

import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.events.impl.domain.model.EventDomainModel
import com.itis.feature.events.impl.domain.model.EventsListDomainModel
import javax.inject.Inject

class EventsListDomainModelMapper @Inject constructor() {

    fun mapResponseToDomainModel(input: EventsListResponse?): EventsListDomainModel? {
        val eventsList = mutableListOf<EventDomainModel>()
        var events: EventsListDomainModel? = null
        input?.let {
            it.events.forEach { eventData ->
                val event = EventDomainModel(
                    id = eventData.id,
                    startDate = eventData.dates[0].startDate,
                    endDate = eventData.dates[0].endDate,
                    title = eventData.title,
                    description = eventData.description,
                    imageUrl = eventData.images[0].imageUrl,
                    favoritesCount = eventData.favoritesCount,
                    commentsCount = eventData.commentsCount
                )
                eventsList.add(event)
            }
            events = EventsListDomainModel(
                count = it.count,
                events = eventsList
            )
        }
        return events
    }
}