package com.example.dinner_time.presentation.restaurant_list

import com.example.dinner_time.domain.model.Restaurant

data class RestaurantListState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val error: String = ""
)
