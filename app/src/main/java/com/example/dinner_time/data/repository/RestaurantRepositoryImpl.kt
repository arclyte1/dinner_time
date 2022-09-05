package com.example.dinner_time.data.repository

import com.example.dinner_time.BuildConfig
import com.example.dinner_time.domain.model.Restaurant
import com.example.dinner_time.data.remote.YandexMapsApi
import com.example.dinner_time.data.remote.dto.toRestaurantList
import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor (
        private val yandexMapsApi: YandexMapsApi
): RestaurantRepository  {

    private var restaurantList: List<Restaurant> = emptyList()

    override suspend fun getRestaurants(startPoint: Geometry, radius: Double): List<Restaurant> {
        val radiusCoordinates = Geometry.kmIntoDeg(startPoint, radius)
        val response = yandexMapsApi.getRestaurants(
            text = "Где поесть",
            type = "biz",
            lang = "ru_RU",
            apikey = BuildConfig.MAPS_API_KEY,
            ll = "${startPoint.longitude},${startPoint.latitude}",
            spn = "${radiusCoordinates.longitude},${radiusCoordinates.latitude}",
            rspn = 1,
            results = 500
        )
        restaurantList = response.toRestaurantList()
        return restaurantList
    }

    override suspend fun getRestaurant(id: Long): Restaurant? = restaurantList.find { it.id == id }

}