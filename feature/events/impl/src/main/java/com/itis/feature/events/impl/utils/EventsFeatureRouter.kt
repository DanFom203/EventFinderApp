package com.itis.feature.events.impl.utils

import com.itis.feature.events.impl.presentation.model.EventUiModel

interface EventsFeatureRouter {
    fun openEventInfoScreenFromEventsScreen(eventUiModel: EventUiModel)

    fun openEventInfoScreenFromFavouriteEventsScreen(eventUiModel: EventUiModel)

    fun openEventsScreenFromEventInfoScreen()

    fun openProfileScreenFromFavouriteEventsScreen()
}