package com.example.dinner_time.data.remote

import com.example.dinner_time.data.remote.dto.RestaurantsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexMapsApi {

    @GET(".")
    suspend fun getRestaurants(
        @Query("text") text: String,
        @Query("type") type: String,
        @Query("lang") lang: String,
        @Query("apikey") apikey: String,
        @Query("ll") ll: String,
        @Query("spn") spn: String,
        @Query("rspn") rspn: Int,
        @Query("results") results: Int
    ) : RestaurantsDto

}