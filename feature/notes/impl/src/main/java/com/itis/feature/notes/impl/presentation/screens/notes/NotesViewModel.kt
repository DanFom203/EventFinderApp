package com.itis.feature.notes.impl.presentation.screens.notes

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.notes.impl.domain.usecase.DeleteUsersNoteUseCase
import com.itis.feature.notes.impl.domain.usecase.GetUsersNotesUseCase
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel (
    private val getUsersNotesUseCase: GetUsersNotesUseCase,
    private val deleteUsersNoteUseCase: DeleteUsersNoteUseCase,
    private val router: NotesFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    preferencesImpl: PreferencesImpl
) : BaseViewModel() {

    private val _currentNotesFlow = MutableStateFlow<List<NoteUiModel>?>(null)
    val currentNotesFlow: StateFlow<List<NoteUiModel>?>
        get() = _currentNotesFlow

    val errorsChannel = Channel<Throwable>()

    private val userId = preferencesImpl.getCurrentUserId()

    fun initialize() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getUsersNotesUseCase.invoke(userId = userId)
            }.onSuccess {
                _currentNotesFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun deleteNote(creationTime: Long) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                deleteUsersNoteUseCase.invoke(creationTime = creationTime)
            }.onSuccess {
                _currentNotesFlow.value = getUsersNotesUseCase.invoke(userId = userId)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openAddNoteScreen() {
        router.openAddNoteScreenFromNotesScreen()
    }
}