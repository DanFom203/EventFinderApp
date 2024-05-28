package com.itis.common.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey
    @ColumnInfo(name = "creation_time")
    val creationTime: Long,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "user_id")
    val userId: String
)