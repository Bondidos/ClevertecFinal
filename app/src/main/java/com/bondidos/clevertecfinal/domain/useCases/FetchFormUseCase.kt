package com.bondidos.clevertecfinal.domain.useCases

import com.bondidos.clevertecfinal.domain.utils.CreateForm
import com.bondidos.clevertecfinal.domain.Repository
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchFormUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<MultiViewModel>> = flow {
        val data = repository.fetchForm()
        val form = CreateForm(data).execute()
        emit(form)
    }
}