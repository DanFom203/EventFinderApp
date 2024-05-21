package com.itis.feature.events.impl.utils

import com.itis.feature.events.impl.presentation.model.EventUiModel

interface EventsFeatureRouter {
    fun openEventInfoScreenFromEventsScreen(eventUiModel: EventUiModel)
}