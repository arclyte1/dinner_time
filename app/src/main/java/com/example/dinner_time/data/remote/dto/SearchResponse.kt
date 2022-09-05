package com.example.dinner_time.data.remote.dto

data class SearchResponse(
    val boundedBy: List<List<Double>>?,
    val display: String?,
    val found: Int
)