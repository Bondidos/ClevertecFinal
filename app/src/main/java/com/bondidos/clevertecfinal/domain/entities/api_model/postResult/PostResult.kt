package com.bondidos.clevertecfinal.domain.entities.api_model.postResult

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResult(
    @Json(name = "result")
    val result: String
)