package com.bondidos.clevertecfinal.domain.repository

import com.bondidos.clevertecfinal.domain.entities.api_model.formFromApi.FormModel
import com.bondidos.clevertecfinal.domain.entities.api_model.postResult.PostResult
import com.bondidos.clevertecfinal.domain.entities.ui_model.PostForm

interface Repository {
    suspend fun fetchForm(): FormModel
    suspend fun postForm(form: PostForm): PostResult
}