package com.bondidos.clevertecfinal.domain.utils

import com.bondidos.clevertecfinal.data.api_model.FormModel
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel

class CreateForm constructor(private val formModel: FormModel) {

    fun execute(): List<MultiViewModel> {
        val form = mutableListOf<MultiViewModel>()
        formModel.fields.forEach { field ->
            when (field.type) {
                TYPE_INPUT_TEXT -> {
                    form.add(MultiViewModel.Name(name = field.name))
                    form.add((MultiViewModel.TextField(name = field.name)))
                }
                TYPE_INPUT_NUM -> {
                    form.add(MultiViewModel.Name(name = field.name))
                    form.add((MultiViewModel.NumberField(name = field.name)))
                }
                TYPE_INPUT_LIST -> {
                    form.add(MultiViewModel.Name(name = field.name))
                    form.add((MultiViewModel.SpinnerField(name = field.name,values = field.values)))
                }
            }
        }
        form.add(MultiViewModel.SubmitField)
        return form.toList()
    }

    val title: String = formModel.title

    val imageUrl: String = formModel.image

    companion object {
        const val TYPE_INPUT_TEXT = "TEXT"
        const val TYPE_INPUT_NUM = "NUMERIC"
        const val TYPE_INPUT_LIST = "LIST"
    }
}