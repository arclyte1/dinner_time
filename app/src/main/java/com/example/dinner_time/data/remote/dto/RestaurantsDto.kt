package com.example.dinner_time.data.remote.dto

import com.example.dinner_time.domain.model.Restaurant

data class RestaurantsDto(
    val features: List<Feature>?,
    val properties: PropertiesX?,
    val type: String
)

fun RestaurantsDto.toRestaurantList(): List<Restaurant> {
    return features?.mapNotNull { it.toRestaurant() } ?: emptyList()
}