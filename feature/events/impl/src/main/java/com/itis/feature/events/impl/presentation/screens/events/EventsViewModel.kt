package com.itis.feature.events.impl.presentation.screens.events

import com.itis.common.base.BaseViewModel
import com.itis.feature.events.impl.data.ExceptionHandlerDelegate
import com.itis.feature.events.impl.utils.EventsFeatureRouter

class EventsViewModel (
//    private val upcomingMatchesUseCase: UpcomingMatchesUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {
}