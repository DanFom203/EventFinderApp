package com.itis.feature.events.api.utils

import com.itis.feature.events.api.presentation.model.EventUiModel

interface EventsFeatureRouter {
    fun openEventInfoScreenFromEventsScreen(eventUiModel: EventUiModel)

    fun openEventInfoScreenFromFavouriteEventsScreen(eventUiModel: EventUiModel)

    fun openEventsScreenFromEventInfoScreen()

    fun openProfileScreenFromFavouriteEventsScreen()
}