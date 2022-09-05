package com.example.dinner_time.data.remote.dto

data class SearchRequest(
    val boundedBy: List<List<Double>>?,
    val request: String,
    val results: Int?,
    val skip: Int?
)