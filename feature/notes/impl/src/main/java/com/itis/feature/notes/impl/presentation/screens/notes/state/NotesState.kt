package com.itis.feature.notes.impl.presentation.screens.notes.state

import com.itis.feature.notes.impl.presentation.model.NoteUiModel

sealed class NotesState {
    object Loading : NotesState()
    data class Success(val notes: List<NoteUiModel>) : NotesState()
    object Empty : NotesState()
    data class Error(val message: String) : NotesState()
}