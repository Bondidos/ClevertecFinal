package com.bondidos.clevertecfinal.domain

import com.bondidos.clevertecfinal.data.api_model.FormModel

interface Repository {
    suspend fun fetchForm(): FormModel
}