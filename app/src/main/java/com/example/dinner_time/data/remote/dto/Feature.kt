package com.example.dinner_time.data.remote.dto

import com.example.dinner_time.domain.model.Restaurant

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

fun Feature.toRestaurant(): Restaurant? {
    return if (properties.CompanyMetaData != null)
        Restaurant(
            id = properties.CompanyMetaData.id.toLong(),
            title = properties.CompanyMetaData.name,
            address = properties.CompanyMetaData.address,
            url = properties.CompanyMetaData.url,
            phones = properties.CompanyMetaData.Phones?.map { it.formatted },
            categories = properties.CompanyMetaData.Categories?.map { it.name },
            geometry = com.example.dinner_time.domain.model.Geometry(geometry.coordinates[0], geometry.coordinates[1])
        )
    else
        null
}