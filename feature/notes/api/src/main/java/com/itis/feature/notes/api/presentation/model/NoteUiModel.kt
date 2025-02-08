package com.itis.feature.notes.api.presentation.model

data class NoteUiModel (
    val creationTime: Long,
    val title: String?,
    val text: String?,
    val userId: String
)