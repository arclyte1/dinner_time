package com.example.dinner_time.domain.repository

import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.model.Restaurant

interface RestaurantRepository {

    suspend fun getRestaurants(startPoint: Geometry, radius: Double): List<Restaurant>

    suspend fun getRestaurant(id: Long): Restaurant?

}