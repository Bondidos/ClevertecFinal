package com.bondidos.clevertecfinal.domain.useCases

import com.bondidos.clevertecfinal.domain.utils.CreateForm
import com.bondidos.clevertecfinal.domain.repository.Repository
import com.bondidos.clevertecfinal.domain.entities.ui_model.UiFormModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchFormUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<UiFormModel> = flow {
        val data = repository.fetchForm()
        val form = CreateForm(data).execute()
        emit(form)
    }
}