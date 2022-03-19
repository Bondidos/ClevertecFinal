package com.bondidos.clevertecfinal.ui.form_fragment.formViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bondidos.clevertecfinal.domain.useCases.FetchFormUseCase
import com.bondidos.clevertecfinal.domain.useCases.PostFormUseCase
import com.bondidos.clevertecfinal.ui.form_fragment.FormViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FormFactory @Inject constructor(
    private val fetchFormUseCase: FetchFormUseCase,
    private val postFormUseCase: PostFormUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormViewModel(fetchFormUseCase,postFormUseCase) as T
    }
}