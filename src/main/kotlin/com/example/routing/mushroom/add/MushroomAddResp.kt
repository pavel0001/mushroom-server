package com.example.routing.mushroom.add

import com.example.models.BaseError
import com.example.models.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class MushroomAddResp(
    val isSuccess: Boolean,
    val fileName: String? = null,
    override val error: BaseError? = null
) : BaseResponse()
