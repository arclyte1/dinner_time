package com.example.dinner_time.domain.model

data class Restaurant(
    val id: Long,
    val title: String,
    val address: String?,
    val url: String?,
    val phones: List<String>?,
    val categories: List<String>?,
    val geometry: Geometry
)