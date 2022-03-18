package com.bondidos.clevertecfinal.domain.useCases

import com.bondidos.clevertecfinal.data.api_model.FormModel
import com.bondidos.clevertecfinal.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchFormUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<FormModel> = flow {
        emit(repository.fetchForm())
    }
}