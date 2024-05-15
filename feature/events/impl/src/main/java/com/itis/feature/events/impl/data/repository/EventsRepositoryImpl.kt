package com.itis.feature.events.impl.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.kudago.api.remote.KudagoApi
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val api: KudagoApi
): EventsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEvents(): EventsListResponse {
        return api.getEvents(location = "kzn")
    }
}