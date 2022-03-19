package com.bondidos.clevertecfinal.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostForm(
    val form: Map<String,String>
)