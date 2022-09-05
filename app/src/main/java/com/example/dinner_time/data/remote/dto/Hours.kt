package com.example.dinner_time.data.remote.dto

data class Hours(
    val Availabilities: List<Availability> = emptyList(),
    val text: String
)