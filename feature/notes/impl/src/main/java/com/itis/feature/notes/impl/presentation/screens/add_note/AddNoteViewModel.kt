package com.itis.feature.notes.impl.presentation.screens.add_note

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.notes.impl.domain.usecase.SaveUsersNoteUseCase
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val saveUsersNoteUseCase: SaveUsersNoteUseCase,
    private val router: NotesFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    preferencesImpl: PreferencesImpl
) : BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val userId = preferencesImpl.getCurrentUserId()

    fun addNote(creationTime: Long, title: String?, text: String?) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                saveUsersNoteUseCase.invoke(
                    NoteUiModel(
                        creationTime = creationTime,
                        title = title,
                        text = text,
                        userId = userId
                    )
                )
            }.onSuccess {
                openNotesScreen()
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openNotesScreen() {
        router.openNotesScreenFromAddNotesScreen()
    }
}