package com.example.models.mushroomAdd

import com.example.models.Mushroom
import kotlinx.serialization.Serializable

@Serializable
data class MushroomAddReq(
    val lat: Double,
    val lon: Double,
    val name: String,
    val description: String,
    val image: String
) {
    fun toMushroom(imageName: String): Mushroom {
        return Mushroom(
            lat = lat,
            lon = lon,
            name = name,
            description = description,
            image = imageName
        )
    }
}
