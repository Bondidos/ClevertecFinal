package com.bondidos.clevertecfinal.data.api_service

import com.bondidos.clevertecfinal.data.api_model.FormModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClevertecApi {
    @GET("/tt/meta/")
    suspend fun fetchForm(): FormModel

    @POST("/tt/data/")
    suspend fun submitForm(@Body body: String): String
}