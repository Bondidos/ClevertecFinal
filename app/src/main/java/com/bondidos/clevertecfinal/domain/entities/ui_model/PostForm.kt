package com.bondidos.clevertecfinal.domain.entities.ui_model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostForm(
    val form: Map<String,String>
)