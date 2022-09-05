package com.example.dinner_time.presentation.start

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinner_time.R
import com.example.dinner_time.databinding.FragmentStartBinding


class StartFragment: Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                updateUI()
            } else {
                binding.button.visibility = View.VISIBLE
                binding.textView2.visibility = View.VISIBLE
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStartBinding.bind(view)
        binding.button.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            updateUI()
        }
        else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    fun updateUI() {
        findNavController().navigate(R.id.action_startFragment_to_restaurantListFragment)
    }
}