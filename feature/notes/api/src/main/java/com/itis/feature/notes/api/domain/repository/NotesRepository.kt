package com.itis.feature.notes.api.domain.repository

import com.itis.common.data.local.db.entity.NoteEntity

interface NotesRepository {

    suspend fun getNotesByUserId(userId: String): List<NoteEntity>?

    suspend fun deleteNoteByCreationTime(creationTime: Long)

    suspend fun saveNoteToDatabase(noteEntity: NoteEntity)
}