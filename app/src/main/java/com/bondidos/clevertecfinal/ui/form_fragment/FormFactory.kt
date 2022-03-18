package com.bondidos.clevertecfinal.ui.form_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bondidos.clevertecfinal.domain.useCases.FetchFormUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FormFactory @Inject constructor(
    private val fetchFormUseCase: FetchFormUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormViewModel(fetchFormUseCase) as T
    }
}