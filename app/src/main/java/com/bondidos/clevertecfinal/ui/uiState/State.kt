package com.bondidos.clevertecfinal.ui.uiState

import com.bondidos.clevertecfinal.data.api_model.FormModel

sealed class State {
    object Initial: State()
    object Loading: State()
    class Success(val data: FormModel): State()
    class Error(val message: String): State()
}