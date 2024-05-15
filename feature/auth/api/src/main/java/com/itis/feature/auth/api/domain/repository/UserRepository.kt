package com.itis.feature.auth.api.domain.repository

import com.itis.feature.auth.api.domain.model.User

interface UserRepository {
    suspend fun createUser(username: String, email: String, password: String, city: String) : User
    suspend fun signIn(email: String,password: String) : User
    suspend fun signOut()
    suspend fun deleteAccount()
}