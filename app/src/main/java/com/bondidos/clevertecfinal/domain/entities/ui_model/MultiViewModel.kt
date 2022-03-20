package com.bondidos.clevertecfinal.domain.entities.ui_model

sealed class MultiViewModel {
    data class Name(
        val name: String,
    ): MultiViewModel()
    data class TextField(val name: String): MultiViewModel()
    data class NumberField(val name: String): MultiViewModel()
    data class SpinnerField(
        val name: String,
        val values: Map<String,String>?
        ): MultiViewModel()
    object SubmitField: MultiViewModel()
}