package com.itis.feature.notes.impl.domain.model

data class NoteDomainModel (
    val creationTime: Long,
    val title: String?,
    val text: String?,
    val userId: String
)