package com.itis.feature.events.impl.presentation.screens.event_info

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.feature.events.impl.data.ExceptionHandlerDelegate
import com.itis.feature.events.impl.data.runCatching
import com.itis.feature.events.impl.domain.usecase.GetEventInfoUseCase
import com.itis.feature.events.impl.presentation.model.EventInfoUiModel
import com.itis.feature.events.impl.presentation.model.EventsListUiModel
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventInfoViewModel (
    private val getEventInfoUseCase: GetEventInfoUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {

    private val _currentEventInfoFlow = MutableStateFlow<EventInfoUiModel?>(null)
    val currentEventInfoFlow: StateFlow<EventInfoUiModel?>
        get() = _currentEventInfoFlow

    val errorsChannel = Channel<Throwable>()

    fun initialize(eventId: Int) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getEventInfoUseCase.invoke(eventId)
            }.onSuccess {
                _currentEventInfoFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
}