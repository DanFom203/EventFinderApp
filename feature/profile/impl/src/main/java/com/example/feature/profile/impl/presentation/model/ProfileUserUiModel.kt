package com.example.feature.profile.impl.presentation.model

data class ProfileUserUiModel(
    val userId: String,
    val avatar: String?,
    val username: String,
    val email: String,
    val city: String
)