package com.example.dinner_time.di

import com.example.dinner_time.common.Constants
import com.example.dinner_time.data.remote.YandexMapsApi
import com.example.dinner_time.data.repository.RestaurantRepositoryImpl
import com.example.dinner_time.domain.repository.RestaurantRepository
import com.example.dinner_time.presentation.restaurant_list.RestaurantListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideYandexMapsApi(): YandexMapsApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexMapsApi::class.java)

    @Provides
    @Singleton
    fun provideRestaurantRepository(api: YandexMapsApi): RestaurantRepository =
        RestaurantRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideRestaurantListAdapter(): RestaurantListAdapter =
        RestaurantListAdapter()

}