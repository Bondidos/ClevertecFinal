package com.bondidos.clevertecfinal.data

import com.bondidos.clevertecfinal.data.api_model.FormModel
import com.bondidos.clevertecfinal.data.api_service.ClevertecApi
import com.bondidos.clevertecfinal.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ClevertecApi
) : Repository {
    override suspend fun fetchForm(): FormModel =
        withContext(Dispatchers.IO) { service.fetchForm() }
}