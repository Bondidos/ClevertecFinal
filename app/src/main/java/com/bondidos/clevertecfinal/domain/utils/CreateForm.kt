package com.bondidos.clevertecfinal.domain.utils

import com.bondidos.clevertecfinal.domain.entities.api_model.formFromApi.FormModel
import com.bondidos.clevertecfinal.domain.entities.ui_model.UiFormModel
import com.bondidos.clevertecfinal.domain.entities.ui_model.MultiViewModel

class CreateForm constructor(private val formModel: FormModel) {

    fun execute(): UiFormModel {
        val form = mutableListOf<MultiViewModel>()
        formModel.fields.forEach { field ->
            when (field.type) {
                TYPE_INPUT_TEXT -> {
                    form.add(MultiViewModel.Name(name = field.title))
                    form.add((MultiViewModel.TextField(name = field.name)))
                }
                TYPE_INPUT_NUM -> {
                    form.add(MultiViewModel.Name(name = field.title))
                    form.add((MultiViewModel.NumberField(name = field.name)))
                }
                TYPE_INPUT_LIST -> {
                    form.add(MultiViewModel.Name(name = field.title))
                    form.add((MultiViewModel.SpinnerField(name = field.name,values = field.values)))
                }
            }
        }
        form.add(MultiViewModel.SubmitField)

        return UiFormModel(
            title = formModel.title,
            image = formModel.image,
            fields = form.toList()
        )
    }

    companion object {
        const val TYPE_INPUT_TEXT = "TEXT"
        const val TYPE_INPUT_NUM = "NUMERIC"
        const val TYPE_INPUT_LIST = "LIST"
    }
}