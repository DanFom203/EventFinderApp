package com.itis.feature.auth.impl.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.itis.feature.auth.api.domain.model.User
import com.itis.feature.auth.api.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : UserRepository {
    override suspend fun createUser(
        username: String,
        email: String,
        password: String,
        city: String
    ): User {

        val createUserTask = auth.createUserWithEmailAndPassword(email, password)

        return suspendCoroutine { continuation ->
            createUserTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User account created & signed in
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                continuation.resume(User(user.displayName ?: "", user.email ?: ""))
                            } else {
                                continuation.resumeWithException(Exception("Error updating user profile: ${updateTask.exception?.message}"))
                            }
                        }
                } else {
                    // Sign in failed
                    continuation.resumeWithException(Exception("Error creating user: ${task.exception?.message}"))
                }
            }
        }
    }

    override suspend fun signIn(email: String, password: String): User {
        val signInTask = auth.signInWithEmailAndPassword(email, password)

        return suspendCoroutine { continuation ->
            signInTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(user?.displayName)
                        .build()
                    continuation.resume(User(user?.email ?: "", user?.displayName ?: ""))
                } else {
                    continuation.resumeWithException(Exception("Error signing in: ${task.exception?.message}"))
                }
            }
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
}