package com.example.database.mushroom

import com.example.models.Mushroom
import kotlinx.serialization.Serializable

@Serializable
data class MushroomDTO(
    val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val description: String,
    val image: String?
) {
    fun toMushroom(): Mushroom {
        return Mushroom(
            id = id,
            lat = lat,
            lon = lon,
            name = name,
            description = description,
            image = image.orEmpty().ifEmpty { "placeholder.png" }
        )
    }
}
