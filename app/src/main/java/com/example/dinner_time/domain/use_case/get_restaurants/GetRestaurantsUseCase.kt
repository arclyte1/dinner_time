package com.example.dinner_time.domain.use_case.get_restaurants

import com.example.dinner_time.common.Resource
import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.model.Restaurant
import com.example.dinner_time.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(startPoint: Geometry, radius: Double): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())
        val restaurants = repository.getRestaurants(startPoint, radius)
        emit(Resource.Success(restaurants))
    }
}