package com.example.models.mushroomAdd

import kotlinx.serialization.Serializable

@Serializable
data class MushroomAddResp(
    val errorCode: String? = null,
    val errorMessage: String? = null,
    val message: String
)
