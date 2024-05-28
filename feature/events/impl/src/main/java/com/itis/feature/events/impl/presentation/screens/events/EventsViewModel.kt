package com.itis.feature.events.impl.presentation.screens.events

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.events.impl.domain.usecase.GetCurrentEventsUseCase
import com.itis.feature.events.impl.presentation.model.EventsListUiModel
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.itis.common.utils.runCatching
import com.itis.feature.events.impl.presentation.model.EventUiModel

class EventsViewModel (
    private val getCurrentEventsUseCase: GetCurrentEventsUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {

    private val _currentEventsFlow = MutableStateFlow<EventsListUiModel?>(null)
    val currentEventsFlow: StateFlow<EventsListUiModel?>
        get() = _currentEventsFlow

    val errorsChannel = Channel<Throwable>()

    fun initialize(location: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getCurrentEventsUseCase.invoke(location = location)
            }.onSuccess {
                _currentEventsFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openEventInfoScreen(eventUiModel: EventUiModel){
        router.openEventInfoScreenFromEventsScreen(eventUiModel)
    }
}