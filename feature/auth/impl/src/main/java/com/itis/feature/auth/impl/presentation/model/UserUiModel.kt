package com.itis.feature.auth.impl.presentation.model

data class UserUiModel(
    private val id: String,
    private val name: String,
    private val email: String,
    private val city: String
)