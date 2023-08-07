package com.example.routing.mushroom.get

import com.example.models.BaseError
import com.example.models.BaseResponse
import com.example.models.Mushroom
import kotlinx.serialization.Serializable

@Serializable
data class MushroomGetResp(
    val isSuccess: Boolean,
    val mushroom: Mushroom? = null,
    override val error: BaseError? = null
): BaseResponse()