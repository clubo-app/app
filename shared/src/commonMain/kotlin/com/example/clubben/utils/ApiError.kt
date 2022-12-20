package com.example.clubben.utils

import kotlinx.serialization.Serializable

val internalServerError = ApiError(code = 500, message = "Internal Server Error")

/**
 * Our custom ApiError that gets automatically passed by Ktor, when Error Code > 200
 */
@Serializable()
data class ApiError(
    val code: Int = 400,
    override val message: String
) : Throwable()