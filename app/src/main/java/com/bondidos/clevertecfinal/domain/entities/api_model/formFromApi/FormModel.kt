package com.bondidos.clevertecfinal.domain.entities.api_model.formFromApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormModel(
    @Json(name = "fields")
    val fields: List<Field>,
    @Json(name = "image")
    val image: String,
    @Json(name = "title")
    val title: String
)