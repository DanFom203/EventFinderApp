package com.itis.feature.events.impl.domain.mapper

import com.itis.feature.events.impl.domain.model.EventInfoDomainModel
import com.itis.feature.events.impl.presentation.model.EventInfoUiModel
import javax.inject.Inject

class EventInfoUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: EventInfoDomainModel?): EventInfoUiModel? {
        return input?.let {
            EventInfoUiModel(
                id = it.id,
                title = it.title,
                address = it.address,
                location = it.location,
                description = it.description,
                categories = it.categories,
                ageRestriction = it.ageRestriction,
                isFree = it.isFree,
                images = it.images,
                favoritesCount = it.favoritesCount,
                commentsCount = it.commentsCount
            )
        }
    }
}