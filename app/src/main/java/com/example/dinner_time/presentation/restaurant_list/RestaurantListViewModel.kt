package com.example.dinner_time.presentation.restaurant_list

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinner_time.common.Resource
import com.example.dinner_time.domain.model.Geometry
import com.example.dinner_time.domain.use_case.get_restaurants.GetRestaurantsUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    @ApplicationContext context: Context,
): AndroidViewModel(context as Application) {


    private val _getRestaurantsState = MutableStateFlow<RestaurantListState>(RestaurantListState())
    val getRestaurantsState: StateFlow<RestaurantListState> = _getRestaurantsState

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    init {
        refreshRestaurants(context)
    }

    fun refreshRestaurants(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null)
                    getRestaurants(
                        Geometry(latitude = location.latitude,
                            longitude = location.longitude),
                        5.0
                    )
            }
    }

    fun getRestaurants(startPoint: Geometry, radius: Double) {
        getRestaurantsUseCase(startPoint, radius).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _getRestaurantsState.value = RestaurantListState(restaurants = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _getRestaurantsState.value = RestaurantListState(error = result.message ?: "Unknown error")
                }
                is Resource.Loading -> {
                    _getRestaurantsState.value = RestaurantListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}