package com.itis.feature.notes.impl.domain.mapper

import com.itis.feature.notes.impl.domain.model.NoteDomainModel
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import javax.inject.Inject

class NoteUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: NoteDomainModel?): NoteUiModel? {
        return input?.let {
            NoteUiModel(
                creationTime = input.creationTime,
                title = input.title,
                text = input.text,
                userId = input.userId
            )
        }
    }
}