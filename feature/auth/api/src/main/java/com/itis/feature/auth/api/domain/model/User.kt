package com.itis.feature.auth.api.domain.model

data class User(
    val userId: String,
    val avatar: String?,
    val username: String,
    val email: String,
    val city: String
)
