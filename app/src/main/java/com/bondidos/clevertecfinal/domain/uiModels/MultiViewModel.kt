package com.bondidos.clevertecfinal.domain.uiModels

sealed class MultiViewModel {
    data class Name(
        val name: String,
    ): MultiViewModel()
    object TextField: MultiViewModel()
    object NumberField: MultiViewModel()
    data class SpinnerField(val values: Map<String,String>?): MultiViewModel()
    object SubmitField: MultiViewModel()
}