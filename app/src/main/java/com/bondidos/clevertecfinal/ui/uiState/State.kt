package com.bondidos.clevertecfinal.ui.uiState

import com.bondidos.clevertecfinal.domain.entities.ui_model.UiFormModel

sealed class State {
    object Initial: State()
    object Loading: State()
    class Success(val data: UiFormModel): State()
    object Error: State()
}