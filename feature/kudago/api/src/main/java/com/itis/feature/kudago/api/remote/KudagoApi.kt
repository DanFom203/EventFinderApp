package com.itis.feature.kudago.api.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.itis.feature.events.api.domain.response.EventsListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.Instant

interface KudagoApi {
    @RequiresApi(Build.VERSION_CODES.O)
    @GET("events")
    suspend fun getEvents (
        @Query("actual_since") actualSince: String = Instant.now().toString(),
        @Query("lang") lang: String = "ru",
        @Query("location") location: String,
        @Query("fields") fields: String = "id,title,dates,description,age_restriction,is_free,images,favorites_count,comments_count"
    ): EventsListResponse
}

//https://kudago.com/public-api/v1.4/events/?
// actual_since=2024-05-14T14%3A40%3A22.260Z
// &lang=en
// &location=kzn
// &fields=id,title,dates,description,age_restriction,is_free,images,favorites_count,comments_count