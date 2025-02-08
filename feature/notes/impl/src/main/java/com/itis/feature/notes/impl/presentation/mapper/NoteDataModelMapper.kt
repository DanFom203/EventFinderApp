package com.itis.feature.notes.impl.presentation.mapper

import com.itis.common.data.local.db.entity.NoteEntity
import com.itis.feature.notes.api.presentation.model.NoteUiModel
import javax.inject.Inject

class NoteDataModelMapper @Inject constructor() {

    fun mapUiToDataModel(input: NoteUiModel?): NoteEntity? {
        return input?.let {
            NoteEntity(
                creationTime = input.creationTime,
                title = input.title,
                text = input.text,
                userId = input.userId
            )
        }
    }
}