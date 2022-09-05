package com.example.dinner_time.presentation.restaurant_details

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dinner_time.R
import com.example.dinner_time.common.Constants
import com.example.dinner_time.databinding.FragmentRestaurantDetailsBinding
import com.example.dinner_time.domain.model.Restaurant
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailsFragment: Fragment(R.layout.fragment_restaurant_details) {

    private lateinit var binding: FragmentRestaurantDetailsBinding

    private val viewModel: RestaurantDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantDetailsBinding.bind(view)
        val restaurantId = requireArguments().getLong(Constants.RESTAURANT_ID_FIELD)
        lifecycleScope.launchWhenStarted {
            viewModel.getRestaurantState.collect {
                binding.loading.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                if (it.restaurant != null)
                    setRestaurant(it.restaurant)

                if (it.error.isNotBlank())
                    Snackbar.make(
                        binding.root,
                        it.error,
                        Snackbar.LENGTH_LONG
                    ).show()

            }
        }

        viewModel.getRestaurant(restaurantId)
    }

    fun setRestaurant(restaurant: Restaurant) {
        binding.title.text = restaurant.title
        binding.categories.text = restaurant.categories?.joinToString()
        binding.address.text = restaurant.address ?: ""
        binding.url.text = restaurant.url ?: "-"
        binding.phoneNumber.text = if (restaurant.phones != null)
            restaurant.phones.joinToString() else "-"

        if (restaurant.url != null) {
            binding.url.setTextColor(Color.BLUE)
            binding.url.setOnClickListener { _ ->
                openWebPage(restaurant.url)
            }
        }

        if (restaurant.phones != null) {
            binding.phoneNumber.setTextColor(Color.BLUE)
//            binding.phoneNumber.setOnClickListener {
//                dialCall(restaurant.phones[0])
//            }
        }

        if (restaurant.address != null) {
            binding.address.setTextColor(Color.BLUE)
            binding.address.setOnClickListener {
                showMap(restaurant.geometry.latitude, restaurant.geometry.longitude)
            }
        }
    }

    fun openWebPage(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    fun dialCall(phoneNumber: String) {
        val uri = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    fun showMap(latitude: Double, longitude: Double) {
        val uri = Uri.parse("geo:$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}