package com.example.dinner_time.domain.use_case.get_restaurant

import com.example.dinner_time.common.Resource
import com.example.dinner_time.domain.model.Restaurant
import com.example.dinner_time.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(id: Long): Flow<Resource<Restaurant>> = flow {
        try {
            emit(Resource.Loading())
            val restaurant = repository.getRestaurant(id)
            if (restaurant != null)
                emit(Resource.Success(restaurant))
            else
                emit(Resource.Error("Not found"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }

    }
}