package com.example.dinner_time.presentation.restaurant_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinner_time.common.Resource
import com.example.dinner_time.domain.use_case.get_restaurant.GetRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val getRestaurantUseCase: GetRestaurantUseCase
): ViewModel() {

    private val _getRestaurantState = MutableStateFlow<RestaurantDetailsState>(
        RestaurantDetailsState()
    )
    val getRestaurantState: StateFlow<RestaurantDetailsState> = _getRestaurantState

    fun getRestaurant(id: Long) {
        getRestaurantUseCase(id).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _getRestaurantState.value = RestaurantDetailsState(restaurant = result.data)
                }
                is Resource.Error -> {
                    _getRestaurantState.value = RestaurantDetailsState(error = result.message ?: "Unknown error")
                }
                is Resource.Loading -> {
                    _getRestaurantState.value = RestaurantDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}