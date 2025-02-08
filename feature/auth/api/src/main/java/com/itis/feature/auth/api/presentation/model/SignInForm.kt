package com.itis.feature.auth.api.presentation.model


data class SignInForm(
    val email: String,
    val password: String
)