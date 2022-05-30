package com.bondidos.clevertecfinal.domain.entities.api_model.formFromApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Field(
    @Json(name = "name")
    val name: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "values")
    val values: Map<String, String>?
)