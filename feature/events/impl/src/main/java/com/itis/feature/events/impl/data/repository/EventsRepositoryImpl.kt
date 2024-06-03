package com.itis.feature.events.impl.data.repository

import android.os.Build
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.data.exceptions.AuthException
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.kudago.api.remote.KudagoApi
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val api: KudagoApi,
    private val db: FirebaseFirestore,
    preferencesImpl: PreferencesImpl
): EventsRepository {

    private val userId = preferencesImpl.getCurrentUserId()

    override suspend fun getEvents(location: String): EventsListResponse {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            api.getEvents(actualSince = Instant.now().toString(), location = location)
        } else {
            val now = Date()
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            val dateString = format.format(now)
            api.getEvents(actualSince = dateString, location = location)
        }
    }

    override suspend fun getEventInfo(eventId: Int): EventInfoResponse {
        return api.getEventInfo(eventId = eventId)
    }

    override suspend fun getFavouriteEvents(): EventsListResponse {
        val documentSnapshot = db.collection("users_favourite_events")
            .document(userId)
            .get()
            .await()

        val eventIds = documentSnapshot.get("eventIds") as? List<Int> ?: emptyList()
        val eventIdsString = eventIds.joinToString(separator = ",")

        return api.getFavouriteEvents(ids = eventIdsString)
    }

    override suspend fun existsInFirestoreDb(eventId: Int): Boolean {
        val querySnapshot = db.collection("users_favourite_events")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val eventIds = document.get("eventIds") as? List<*> ?: emptyList<Any>()
            if (eventIds.any { it.toString().toIntOrNull() == eventId }) {
                return true
            }
        }
        return false
    }

    override suspend fun deleteFromFirestoreDb(eventId: Int) {
        val documentReference = db.collection("users_favourite_events").document(userId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(documentReference)
            val eventIds = snapshot.get("eventIds") as? MutableList<Long> ?: mutableListOf()

            if (eventIds.contains(eventId.toLong())) {
                eventIds.remove(eventId.toLong())
            }

            transaction.update(documentReference, "eventIds", eventIds)
        }.await()
    }


    override suspend fun saveToFirestoreDb(eventId: Int) {
        val documentReference = db.collection("users_favourite_events").document(userId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(documentReference)
            val eventIds = if (snapshot.exists()) {
                snapshot.get("eventIds") as? MutableList<Long> ?: mutableListOf()
            } else {
                mutableListOf()
            }

            if (!eventIds.contains(eventId.toLong())) {
                eventIds.add(eventId.toLong())
                transaction.set(documentReference, mapOf("eventIds" to eventIds, "userId" to userId))
            }
        }.await()
    }

    override suspend fun getUserLocation(): String {
        val userDocRef = db.collection("users").document(userId)
        val document = userDocRef.get().await()

        if (document.exists()) {
            return document.getString("city") ?: ""
        } else {
            throw AuthException.InvalidCredentials("User data not found in Firestore")
        }
    }
}