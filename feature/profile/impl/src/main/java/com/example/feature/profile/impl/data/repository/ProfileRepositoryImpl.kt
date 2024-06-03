package com.example.feature.profile.impl.data.repository

import android.net.Uri
import com.example.feature.profile.api.domain.model.ProfileUserDataModel
import com.example.feature.profile.api.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itis.common.data.exceptions.AuthException
import com.itis.common.data.exceptions.CredentialsUpdateException
import com.itis.common.data.storage.PreferencesImpl
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val preferencesImpl: PreferencesImpl,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
): ProfileRepository {
    private val userId = preferencesImpl.getCurrentUserId()

    override suspend fun getUserProfileInfo(): ProfileUserDataModel {
        val userDocRef = db.collection("users").document(userId)
        val document = userDocRef.get().await()

        if (document.exists()) {
            val dbUid = document.getString("uid") ?: ""
            val dbUsername = document.getString("username") ?: ""
            val dbCity = document.getString("city") ?: ""
            val dbEmail = document.getString("email") ?: ""
            val dbAvatar = document.getString("avatar")

            return ProfileUserDataModel(
                userId = dbUid,
                avatar = dbAvatar,
                username = dbUsername,
                email = dbEmail,
                city = dbCity
            )
        } else {
            throw AuthException.InvalidCredentials("User data not found in Firestore")
        }
    }

    override suspend fun uploadImageToFirebaseStorage(imageUri: Uri): String {
        val storageReference = storage.reference.child("user_profile_picture").child("$userId.jpg")
        storageReference.putFile(imageUri).await()
        val uri = storageReference.downloadUrl.await().toString()
        updateAvatarFieldInFirestore(uri)
        return uri
    }

    private suspend fun updateAvatarFieldInFirestore(avatar: String) {
        val document = db.collection("users").document(userId)
        document.update(mapOf("avatar" to avatar)).await()
    }

    override suspend fun updateUserCredentials(
        username: String?,
        email: String?,
        city: String?
    ): Map<String, Boolean?> {
        val user = auth.currentUser ?: throw AuthException.InvalidCredentials("You need to do a re-authentication")
        val operationsSuccess = mutableMapOf<String, Boolean?>()
        operationsSuccess["password_reset"] = null
        operationsSuccess["username_city_update"] = null

        // Password Reset FirebaseAuth
        try {
            if (!email.isNullOrBlank() && (user.email.equals(email, ignoreCase = true))) {
                auth.sendPasswordResetEmail(email).await()
                operationsSuccess["password_reset"] = true
            } else if (!email.isNullOrBlank() && !(user.email.equals(email, ignoreCase = true))) {
                throw CredentialsUpdateException("Enter your REGISTERED email address")
            }
        } catch (e: Exception) {
            operationsSuccess["password_reset"] = false
        }


        // Update Firestore
        val userRef = db.collection("users").document(userId)
        val updates = mutableMapOf<String, Any>()

        if (!username.isNullOrBlank()) {
            updates["username"] = username
        }
        if (!city.isNullOrBlank() && city != "Do Not Change") {
            updates["city"] = city
        }

        try {
            if (updates.isNotEmpty()) {
                operationsSuccess["username_city_update"] = true
                userRef.update(updates).await()
            }
        } catch (e: Exception) {
            operationsSuccess["username_city_update"] = false
        }

        return operationsSuccess
    }

    override suspend fun logoutUser() {
        preferencesImpl.saveCurrentUserId(null)
        preferencesImpl.saveAuthStatus(false)
        auth.signOut()
    }
}