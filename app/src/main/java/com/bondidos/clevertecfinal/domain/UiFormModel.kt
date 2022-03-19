package com.bondidos.clevertecfinal.domain

import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel

data class UiFormModel (
    val title: String,
    val image: String,
    val fields: List<MultiViewModel>
        )