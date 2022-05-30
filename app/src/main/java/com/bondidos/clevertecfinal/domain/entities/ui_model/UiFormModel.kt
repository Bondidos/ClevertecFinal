package com.bondidos.clevertecfinal.domain.entities.ui_model

import com.bondidos.clevertecfinal.domain.entities.ui_model.MultiViewModel

data class UiFormModel (
    val title: String,
    val image: String,
    val fields: List<MultiViewModel>
        )