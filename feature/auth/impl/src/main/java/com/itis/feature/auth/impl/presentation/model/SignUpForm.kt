package com.itis.feature.auth.impl.presentation.model

data class SignUpForm (
    val username: String,
    val email: String,
    val password: String,
    val city: String
)