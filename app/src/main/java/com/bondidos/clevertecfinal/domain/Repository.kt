package com.bondidos.clevertecfinal.domain

import com.bondidos.clevertecfinal.data.api_model.formFromApi.FormModel
import com.bondidos.clevertecfinal.data.api_model.postResult.PostResult

interface Repository {
    suspend fun fetchForm(): FormModel
    suspend fun postForm(form: PostForm): PostResult
}