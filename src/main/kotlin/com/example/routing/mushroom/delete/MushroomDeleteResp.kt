package com.example.routing.mushroom.delete

import com.example.models.BaseError
import com.example.models.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class MushroomDeleteResp(
    val isSuccess: Boolean,
    override val error: BaseError? = null
) : BaseResponse()
