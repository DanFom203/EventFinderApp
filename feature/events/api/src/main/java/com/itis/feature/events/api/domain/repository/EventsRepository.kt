package com.itis.feature.events.api.domain.repository

import com.itis.feature.events.api.domain.response.EventsListResponse

interface EventsRepository {

    suspend fun getEvents(): EventsListResponse

}