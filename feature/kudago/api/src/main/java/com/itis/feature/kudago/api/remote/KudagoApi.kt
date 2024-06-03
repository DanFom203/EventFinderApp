package com.itis.feature.kudago.api.remote

import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.domain.response.EventsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KudagoApi {
    @GET("events")
    suspend fun getEvents (
        @Query("actual_since") actualSince: String,
        @Query("lang") lang: String = "ru",
        @Query("location") location: String,
        @Query("fields") fields: String = "id,title,dates,description,age_restriction,is_free,images,favorites_count,comments_count"
    ): EventsListResponse

    @GET("events")
    suspend fun getFavouriteEvents (
        @Query("lang") lang: String = "ru",
        @Query("ids") ids: String,
        @Query("fields") fields: String = "id,title,dates,description,age_restriction,is_free,images,favorites_count,comments_count"
    ): EventsListResponse

    @GET("events/{event_id}")
    suspend fun getEventInfo (
        @Path("event_id") eventId: Int,
        @Query("lang") lang: String = "ru",
        @Query("fields") fields: String = "id,title,categories,dates,description,age_restriction,place,is_free,images,favorites_count,comments_count",
        @Query("expand") expand: String = "place"
    ): EventInfoResponse
}

/*
1 request example:

https://kudago.com/public-api/v1.4/events/?
actual_since=2024-05-14T14%3A40%3A22.260Z
&lang=ru
&location=kzn
&fields=id,title,dates,description,age_restriction,is_free,images,favorites_count,comments_count

2 request example:
https://kudago.com/public-api/v1.4/events/60843/?
lang=ru
&fields=id,title,categories,dates,description,age_restriction,place,is_free,images,favorites_count,comments_count
&expand=place
*/