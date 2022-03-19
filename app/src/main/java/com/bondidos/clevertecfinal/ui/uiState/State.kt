package com.bondidos.clevertecfinal.ui.uiState

import com.bondidos.clevertecfinal.domain.UiFormModel

sealed class State {
    object Initial: State()
    object Loading: State()
    class Success(val data: UiFormModel): State()
    class Posted(val message: String): State()
    class Error(val message: String): State()
}