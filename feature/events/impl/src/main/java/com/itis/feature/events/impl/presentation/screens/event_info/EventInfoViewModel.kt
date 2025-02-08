package com.itis.feature.events.impl.presentation.screens.event_info

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.events.impl.domain.usecase.DeleteFromFavouritesUseCase
import com.itis.feature.events.impl.domain.usecase.ExistsInFavouritesUseCase
import com.itis.feature.events.impl.domain.usecase.GetEventInfoUseCase
import com.itis.feature.events.impl.domain.usecase.SaveToFavouritesUseCase
import com.itis.feature.events.api.presentation.model.EventInfoUiModel
import com.itis.feature.events.api.utils.EventsFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventInfoViewModel (
    private val getEventInfoUseCase: GetEventInfoUseCase,
    private val saveToFavouritesUseCase: SaveToFavouritesUseCase,
    private val deleteFromFavouritesUseCase: DeleteFromFavouritesUseCase,
    private val existsInFavouritesUseCase: ExistsInFavouritesUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {

    private val _currentEventInfoFlow = MutableStateFlow<EventInfoUiModel?>(null)
    val currentEventInfoFlow: StateFlow<EventInfoUiModel?>
        get() = _currentEventInfoFlow

    val errorsChannel = Channel<Throwable>()

    fun initialize(eventId: Int) {
        viewModelScope.launch {
            var isLiked = false
            runCatching(exceptionHandlerDelegate) {
                isLiked = existsInFavouritesUseCase.invoke(eventId = eventId)
                getEventInfoUseCase.invoke(eventId = eventId)
            }.onSuccess {
                _currentEventInfoFlow.value = it.copy(isLiked = isLiked)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }


    fun saveToFirestoreDb(eventId: Int) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                saveToFavouritesUseCase.invoke(eventId = eventId)
            }.onSuccess {
                _currentEventInfoFlow.value = _currentEventInfoFlow.value?.copy(isLiked = true)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun deleteFromFirestoreDb(eventId: Int) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                deleteFromFavouritesUseCase.invoke(eventId = eventId)
            }.onSuccess {
                _currentEventInfoFlow.value = _currentEventInfoFlow.value?.copy(isLiked = false)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openEvents() {
        router.openEventsScreenFromEventInfoScreen()
    }
}