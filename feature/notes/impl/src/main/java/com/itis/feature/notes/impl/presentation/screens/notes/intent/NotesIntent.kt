package com.itis.feature.notes.impl.presentation.screens.notes.intent

sealed class NotesIntent {
    object LoadNotes : NotesIntent()
    data class DeleteNote(val creationTime: Long) : NotesIntent()
    object OpenAddNoteScreen : NotesIntent()
}