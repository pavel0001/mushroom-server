package com.example.routing.mushroom.getAll

import com.example.models.BaseError
import com.example.models.BaseResponse
import com.example.models.Mushroom
import kotlinx.serialization.Serializable

@Serializable
data class MushroomGetAllResp(
    val mushrooms: List<Mushroom>? = null,
    override val error: BaseError? = null
) : BaseResponse()