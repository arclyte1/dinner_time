package com.example.dinner_time.domain.model
import kotlin.math.PI

data class Geometry(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun kmIntoDeg(geometry: Geometry, kmValue: Double): Geometry {
            val latitude = kmValue / 111.134861111
            val longitude = kmValue / (111.321377778 * kotlin.math.cos(
                geometry.longitude * PI / 180
            ))
            return Geometry(latitude, longitude)
        }
    }
}
