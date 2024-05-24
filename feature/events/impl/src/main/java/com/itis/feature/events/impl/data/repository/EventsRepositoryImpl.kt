package com.itis.feature.events.impl.data.repository

import android.os.Build
import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.kudago.api.remote.KudagoApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val api: KudagoApi
): EventsRepository {
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
}