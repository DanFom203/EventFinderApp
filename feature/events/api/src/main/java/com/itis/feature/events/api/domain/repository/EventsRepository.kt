package com.itis.feature.events.api.domain.repository

import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.domain.response.EventsListResponse

interface EventsRepository {

    suspend fun getEvents(location: String): EventsListResponse

    suspend fun getEventInfo(eventId: Int): EventInfoResponse

}