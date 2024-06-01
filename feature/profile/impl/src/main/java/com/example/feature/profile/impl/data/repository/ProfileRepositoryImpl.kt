package com.example.feature.profile.impl.data.repository

import com.example.feature.profile.api.domain.model.ProfileUserDataModel
import com.example.feature.profile.api.domain.repository.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.data.exceptions.AuthException
import com.itis.common.data.storage.PreferencesImpl
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    private val preferencesImpl: PreferencesImpl,
    private val db: FirebaseFirestore
): ProfileRepository {
    override suspend fun getUserProfileInfo(): ProfileUserDataModel {
        val userId = preferencesImpl.getCurrentUserId()
        val userDocRef = db.collection("users").document(userId)

        return suspendCoroutine { continuation ->
            userDocRef.get().addOnCompleteListener { docTask ->
                if (docTask.isSuccessful) {
                    val document = docTask.result
                    if (document != null && document.exists()) {
                        val dbUid = document.getString("uid") ?: ""
                        val dbUsername = document.getString("username") ?: ""
                        val dbCity = document.getString("city") ?: ""
                        val dbEmail = document.getString("email") ?: ""
//                        val dbAvatar = document.getString("avatar")

                        continuation.resume(
                            ProfileUserDataModel(
                                userId = dbUid,
                                avatar = null,
                                username = dbUsername,
                                email = dbEmail,
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
        }
    }
}