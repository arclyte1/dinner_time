package com.example.dinner_time

import com.example.dinner_time.common.Constants
import com.example.dinner_time.common.Resource
import com.example.dinner_time.data.remote.YandexMapsApi
import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.repository.RestaurantRepository
import com.example.dinner_time.domain.use_case.get_restaurants.GetRestaurantsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetRestaurantsUnitTest {

    private lateinit var api: YandexMapsApi
    private lateinit var repository: RestaurantRepository
    private lateinit var getRestaurantsUseCase: GetRestaurantsUseCase

    @Before
    fun setUp() = runBlocking {
        api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YandexMapsApi::class.java)
        repository = TestRestaurantRepositoryImpl(api)
        getRestaurantsUseCase = GetRestaurantsUseCase(repository)
    }

    @Test
    fun `Test getting list of restaurants with valid geometry`() = runBlocking {
        getRestaurantsUseCase(Geometry(53.4343, 23.8586), 10.0).collect {
            assertFalse(it is Resource.Error)
            if (it is Resource.Success)
                assertEquals(it.data!![0].id, 137639399968)
        }
    }

    @Test
    fun `Test getting list of restaurants with not valid geometry`() = runBlocking {
        getRestaurantsUseCase(Geometry(312.1, 23.8586), 10.0).collect {
            assertFalse(it is Resource.Success)
            if (it is Resource.Error)
                assertEquals(it.message, "HTTP 400 ")
        }
    }

}