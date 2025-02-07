package com.itis.feature.notes.impl.presentation.screens.notes

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.notes.impl.domain.usecase.DeleteUsersNoteUseCase
import com.itis.feature.notes.impl.domain.usecase.GetUsersNotesUseCase
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import com.itis.feature.notes.impl.presentation.screens.notes.intent.NotesIntent
import com.itis.feature.notes.impl.presentation.screens.notes.state.NotesState
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getUsersNotesUseCase: GetUsersNotesUseCase,
    private val deleteUsersNoteUseCase: DeleteUsersNoteUseCase,
    private val router: NotesFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    preferencesImpl: PreferencesImpl
) : BaseViewModel() {

    private val _state = MutableStateFlow<NotesState>(NotesState.Loading)
    val state: StateFlow<NotesState> = _state

    private val _intentChannel = Channel<NotesIntent>(Channel.UNLIMITED)
    val intentChannel = _intentChannel.receiveAsFlow()

    private val userId = preferencesImpl.getCurrentUserId()

    init {
        handleIntent()
    }

    fun sendIntent(intent: NotesIntent) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intentChannel.collect { intent ->
                when (intent) {
                    is NotesIntent.LoadNotes -> loadNotes()
                    is NotesIntent.DeleteNote -> deleteNote(intent.creationTime)
                    is NotesIntent.OpenAddNoteScreen -> router.openAddNoteScreenFromNotesScreen()
                }
            }
        }
    }

    private suspend fun loadNotes() {
        runCatching(exceptionHandlerDelegate) {
            getUsersNotesUseCase.invoke(userId = userId)
        }.onSuccess { notes ->
            _state.value = if (notes.isNotEmpty()) NotesState.Success(notes) else NotesState.Empty
        }.onFailure {
            _state.value = NotesState.Error(it.message ?: "Unknown error")
        }
    }

    private suspend fun deleteNote(creationTime: Long) {
        runCatching(exceptionHandlerDelegate) {
            deleteUsersNoteUseCase.invoke(creationTime)
        }.onSuccess {
            loadNotes()
        }.onFailure {
            _state.value = NotesState.Error(it.message ?: "Failed to delete note")
        }
    }
}
