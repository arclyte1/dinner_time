package com.example.dinner_time.data.remote.dto

data class CompanyMetaData(
    val Categories: List<Category>?,
//    val Hours: Hours,
    val Phones: List<Phone>?,
    val address: String?,
    val id: String,
    val name: String,
    val url: String?
)