package com.example.dinner_time

import com.example.dinner_time.common.Constants
import com.example.dinner_time.common.Resource
import com.example.dinner_time.data.remote.YandexMapsApi
import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.repository.RestaurantRepository
import com.example.dinner_time.domain.use_case.get_restaurant.GetRestaurantUseCase
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetRestaurantUnitTest {

    private lateinit var api: YandexMapsApi
    private lateinit var repository: RestaurantRepository
    private lateinit var getRestaurantUseCase: GetRestaurantUseCase

    @Before
    fun setUp() = runBlocking {
        api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexMapsApi::class.java)
        repository = TestRestaurantRepositoryImpl(api)
        repository.getRestaurants(Geometry(53.4343, 23.8586), 10.0)
        getRestaurantUseCase = GetRestaurantUseCase(repository)
    }

    @Test
    fun `Test getting existing restaurant by id`() = runBlocking {
        getRestaurantUseCase(137639399968).collect {
            assertFalse(it is Resource.Error)
            if (it is Resource.Success)
                assertEquals(it.data!!.title, "Кафе")
        }
    }

    @Test
    fun `Test getting not existing restaurant by id`() = runBlocking {
        getRestaurantUseCase(123423423414234213).collect {
            assertFalse(it is Resource.Success)
            if (it is Resource.Error)
                assertEquals(it.message, "Not found")
        }
    }
}