package com.itis.feature.events.impl.data.mapper

import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.impl.domain.model.EventInfoDomainModel
import javax.inject.Inject

class EventInfoDomainModelMapper @Inject constructor() {

    fun mapResponseToDomainModel(input: EventInfoResponse?): EventInfoDomainModel? {
        return input?.let {
            EventInfoDomainModel(
                id = it.id,
                title = it.title,
                address = it.place?.address ?: "Unknown address",
                location = it.place?.location ?: "Unknown location",
                description = it.description,
                categories = it.categories,
                ageRestriction = it.ageRestriction ?: "No age restriction",
                isFree = it.isFree,
                images = it.images,
                favoritesCount = it.favoritesCount,
                commentsCount = it.commentsCount,
                startDate = it.dates[0].startDate,
                endDate = it.dates[0].endDate,
            )
        }
    }
}