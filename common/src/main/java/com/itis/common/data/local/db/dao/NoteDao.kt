package com.itis.common.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.common.data.local.db.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY creation_time")
    fun getAllNotesOrderedByCreationTime(): List<NoteEntity>?

    @Query("DELETE FROM notes WHERE creation_time = :creationTime")
    fun deleteNoteByCreationTime(creationTime: Long)
}