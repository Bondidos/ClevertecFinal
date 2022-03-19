package com.bondidos.clevertecfinal.ui.uiState

import com.bondidos.clevertecfinal.data.api_model.FormModel
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel

sealed class State {
    object Initial: State()
    object Loading: State()
    class Success(val data: List<MultiViewModel>): State()
    class Error(val message: String): State()
}