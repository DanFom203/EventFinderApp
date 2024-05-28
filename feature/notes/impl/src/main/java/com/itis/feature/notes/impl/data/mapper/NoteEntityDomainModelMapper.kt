package com.itis.feature.notes.impl.data.mapper

import com.itis.common.data.local.db.entity.NoteEntity
import com.itis.feature.notes.impl.domain.model.NoteDomainModel
import javax.inject.Inject

class NoteEntityDomainModelMapper @Inject constructor() {

    fun mapDataToDomainModel(input: NoteEntity?): NoteDomainModel? {
        return input?.let {
            NoteDomainModel(
                creationTime = input.creationTime,
                title = input.title,
                text = input.text,
                userId = input.userId
            )
        }
    }
}