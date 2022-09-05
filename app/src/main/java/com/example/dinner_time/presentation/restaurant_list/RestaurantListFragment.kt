package com.example.dinner_time.presentation.restaurant_list

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dinner_time.R
import com.example.dinner_time.common.Constants
import com.example.dinner_time.databinding.FragmentRastaurantListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantListFragment: Fragment(R.layout.fragment_rastaurant_list) {

    @Inject
    lateinit var restaurantListAdapter: RestaurantListAdapter

    private lateinit var binding: FragmentRastaurantListBinding

    private val viewModel: RestaurantListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRastaurantListBinding.bind(view)

        binding.refresh.setOnRefreshListener {
            refreshList()
        }

        restaurantListAdapter.onClickListener = RestaurantListAdapter.OnClickListener {
            showRestaurant(it)
        }
        binding.recyclerView.adapter = restaurantListAdapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            requireActivity().finishAndRemoveTask()

        lifecycleScope.launchWhenStarted {
            viewModel.getRestaurantsState.collect {

                binding.loading.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                if (it.restaurants.isNotEmpty())
                    restaurantListAdapter.submitList(it.restaurants)

                if (it.error.isNotBlank())
                    Snackbar.make(
                        binding.root,
                        it.error,
                        Snackbar.LENGTH_LONG
                    ).show()

            }
        }


    }

    fun refreshList() {
        requireActivity().finishAndRemoveTask()
        viewModel.refreshRestaurants(requireContext())
        binding.refresh.isRefreshing = false
    }

    fun showRestaurant(restaurantId: Long) {
        val bundle = bundleOf(Constants.RESTAURANT_ID_FIELD to restaurantId)
        findNavController().navigate(R.id.action_restaurantListFragment_to_restaurantDetailsFragment, bundle)
    }

}