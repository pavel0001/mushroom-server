package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseError(val errorCode: String, val errorMessage: String) {
    companion object {
        const val DEFAULT_ERROR = "31"
        const val EXCEPTION_ERROR = "32"
        fun fromException(ex: Exception): BaseError {
            return BaseError(errorCode = EXCEPTION_ERROR, errorMessage = ex.message.orEmpty())
        }

        fun defaultError(message: String): BaseError {
            return BaseError(errorCode = DEFAULT_ERROR, errorMessage = message)
        }
    }

}