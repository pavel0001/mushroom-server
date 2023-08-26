package com.example.routing.mushroom.update

import com.example.models.BaseError
import com.example.models.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class MushroomUpdateResp(
    val isSuccess: Boolean,
    override val error: BaseError? = null
) : BaseResponse()
