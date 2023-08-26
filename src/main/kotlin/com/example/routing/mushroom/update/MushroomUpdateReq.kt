package com.example.routing.mushroom.update

import kotlinx.serialization.Serializable

@Serializable
data class MushroomUpdateReq(
    val id: Long,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val description: String?,
    var image: String?,
) {
    fun updateImage(imagePath: String) {
        image = imagePath
    }
}
