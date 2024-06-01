package com.example.feature.profile.api.domain.model

data class ProfileUserDataModel(
    val userId: String,
    val avatar: String?,
    val username: String,
    val email: String,
    val city: String
)
