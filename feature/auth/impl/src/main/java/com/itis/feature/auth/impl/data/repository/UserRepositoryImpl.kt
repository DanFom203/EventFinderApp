package com.itis.feature.auth.impl.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.data.exceptions.AuthException
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.auth.api.domain.model.User
import com.itis.feature.auth.api.domain.repository.UserRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val preferencesImpl: PreferencesImpl,
    private val db: FirebaseFirestore
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
                    val user = auth.currentUser
                    // User account created
                    saveToFirestoreDb(user!!.uid, username, email, city)
                    continuation.resume(User(user.uid, username, email, city))
                } else {
                    // Sign up failed
                    continuation.resumeWithException(
                        AuthException.InvalidCredentials("Unable to create new account with these credentials")
                    )
                }
            }
        }
    }

    override suspend fun signIn(email: String, password: String): User {
        val signInTask = auth.signInWithEmailAndPassword(email, password)

        return suspendCoroutine { continuation ->
            signInTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userDocRef = db.collection("users").document(user.uid)
                        userDocRef.get().addOnCompleteListener { docTask ->
                            if (docTask.isSuccessful) {
                                val document = docTask.result
                                if (document != null && document.exists()) {
                                    val dbUsername = document.getString("username") ?: ""
                                    val dbCity = document.getString("city") ?: ""

                                    preferencesImpl.saveCurrentUserId(user.uid)
                                    continuation.resume(
                                        User(
                                            userId = user.uid,
                                            username = dbUsername,
                                            email = email,
                                            city = dbCity
                                        )
                                    )
                                } else {
                                    continuation.resumeWithException(
                                        AuthException.InvalidCredentials("User data not found in Firestore")
                                    )
                                }
                            } else {
                                continuation.resumeWithException(
                                    AuthException.InvalidCredentials("Error retrieving user data from Firestore")
                                )
                            }
                        }
                    } else {
                        continuation.resumeWithException(
                            AuthException.InvalidCredentials("User sign-in succeeded but currentUser is null")
                        )
                    }
                } else {
                    continuation.resumeWithException(
                        AuthException.InvalidCredentials("Unable to login with these credentials")
                    )
                }
            }
        }
    }

    private fun saveToFirestoreDb(userId: String, username: String, email: String, city: String) {
        val dbUser = User(
            userId = userId,
            username = username,
            email = email,
            city = city
        )

        db.collection("users")
            .document(userId)
            .set(dbUser)
    }

}