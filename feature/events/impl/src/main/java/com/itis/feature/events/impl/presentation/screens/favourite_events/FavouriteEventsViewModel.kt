package com.itis.feature.events.impl.presentation.screens.favourite_events

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.events.impl.domain.usecase.GetUsersFavouriteEventsUseCase
import com.itis.feature.events.impl.presentation.model.EventUiModel
import com.itis.feature.events.impl.presentation.model.EventsListUiModel
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteEventsViewModel (
    private val getUsersFavouriteEventsUseCase: GetUsersFavouriteEventsUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {
    private val _currentUsersFavouriteEventsFlow = MutableStateFlow<EventsListUiModel?>(null)
    val currentUsersFavouriteEventsFlow: StateFlow<EventsListUiModel?>
        get() = _currentUsersFavouriteEventsFlow

    val errorsChannel = Channel<Throwable>()

    fun initialize() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getUsersFavouriteEventsUseCase.invoke()
            }.onSuccess {
                _currentUsersFavouriteEventsFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openEventInfoScreen(eventUiModel: EventUiModel){
        router.openEventInfoScreenFromFavouriteEventsScreen(eventUiModel)
    }

    fun openProfileScreen() {
        router.openProfileScreenFromFavouriteEventsScreen()
    }
}