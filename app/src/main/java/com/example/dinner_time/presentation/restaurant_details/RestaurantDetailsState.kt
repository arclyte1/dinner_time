package com.example.dinner_time.presentation.restaurant_details

import com.example.dinner_time.domain.model.Restaurant

data class RestaurantDetailsState(
    val isLoading: Boolean = false,
    val restaurant: Restaurant? = null,
    val error: String = ""
)