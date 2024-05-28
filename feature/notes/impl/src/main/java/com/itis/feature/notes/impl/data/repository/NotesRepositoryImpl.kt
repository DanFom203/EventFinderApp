package com.itis.feature.notes.impl.data.repository

import com.itis.common.data.local.db.dao.NoteDao
import com.itis.common.data.local.db.entity.NoteEntity
import com.itis.feature.notes.api.domain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NotesRepository {
    override suspend fun getNotesByUserId(userId: String): List<NoteEntity>? {
        return noteDao.getAllNotesOrderedByCreationTime()
    }

    override suspend fun deleteNoteByCreationTime(creationTime: Long) {
        noteDao.deleteNoteByCreationTime(creationTime)
    }

    override suspend fun saveNoteToDatabase(noteEntity: NoteEntity) {
        noteDao.addNote(noteEntity)
    }
}