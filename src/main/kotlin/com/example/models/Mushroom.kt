package com.example.models

import com.example.database.mushroom.MushroomDTO
import kotlinx.serialization.Serializable

@Serializable
data class Mushroom(
    val lat: Double,
    val lon: Double,
    val name: String,
    val description: String,
    val image: String
) {
    fun toMushroomDTO(): MushroomDTO {
        return MushroomDTO(
            id = 0,
            lat = lat,
            lon = lon,
            name = name,
            description = description,
            image = image
        )
    }
}
