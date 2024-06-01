package com.example.feature.profile.impl.domain.model

data class ProfileUserDomainModel(
    val userId: String,
    val avatar: String?,
    val username: String,
    val email: String,
    val city: String
)