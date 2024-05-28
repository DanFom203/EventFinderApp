package com.itis.feature.events.impl.presentation.screens.event_info

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.events.api.domain.request.UsersFavouriteEventsRequest
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.events.impl.domain.usecase.GetEventInfoUseCase
import com.itis.feature.events.impl.presentation.model.EventInfoUiModel
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventInfoViewModel (
    private val getEventInfoUseCase: GetEventInfoUseCase,
    private val router: EventsFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val db: FirebaseFirestore,
    preferencesImpl: PreferencesImpl,
) : BaseViewModel() {

    private val _currentEventInfoFlow = MutableStateFlow<EventInfoUiModel?>(null)
    val currentEventInfoFlow: StateFlow<EventInfoUiModel?>
        get() = _currentEventInfoFlow

    val errorsChannel = Channel<Throwable>()

    private val userId = preferencesImpl.getCurrentUserId()

    fun initialize(eventId: Int) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getEventInfoUseCase.invoke(eventId)
            }.onSuccess {
                _currentEventInfoFlow.value = it.copy(isLiked = existsInFirestoreDb(eventId))
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun saveToFirestoreDb(eventId: Int) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                db.collection(USERS_FAVOURITE_EVENTS_COLLECTION)
                    .add(
                        UsersFavouriteEventsRequest(
                            userId = userId,
                            eventId = eventId
                        )
                    )
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
                val querySnapshot = db.collection(USERS_FAVOURITE_EVENTS_COLLECTION)
                    .whereEqualTo(USER_ID, userId)
                    .whereEqualTo(EVENT_ID, eventId)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    db.collection(USERS_FAVOURITE_EVENTS_COLLECTION).document(document.id).delete().await()
                }
            }.onSuccess {
                _currentEventInfoFlow.value = _currentEventInfoFlow.value?.copy(isLiked = false)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    private suspend fun existsInFirestoreDb(eventId: Int): Boolean {
        val querySnapshot = db.collection(USERS_FAVOURITE_EVENTS_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .whereEqualTo(EVENT_ID, eventId)
            .get()
            .await()
        return !querySnapshot.isEmpty
    }

    fun openEvents() {
        router.openEventsScreenFromEventInfoScreen()
    }

    companion object {
        private const val USERS_FAVOURITE_EVENTS_COLLECTION = "users_favourite_events"
        private const val USER_ID = "userId"
        private const val EVENT_ID = "eventId"
    }
}