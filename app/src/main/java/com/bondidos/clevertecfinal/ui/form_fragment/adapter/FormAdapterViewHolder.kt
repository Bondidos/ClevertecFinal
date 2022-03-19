package com.bondidos.clevertecfinal.ui.form_fragment.adapter

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bondidos.clevertecfinal.databinding.*
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel
import com.bondidos.clevertecfinal.ui.events.FormEvent

class FormAdapterViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MultiViewModel, emitEvent: (FormEvent)-> Unit) {
        when (model) {
            is MultiViewModel.Name -> bindName(model)
            is MultiViewModel.TextField -> bindTextField(model,emitEvent)
            is MultiViewModel.NumberField -> bindNumberField(model,emitEvent)
            is MultiViewModel.SpinnerField -> bindSpinner(model,emitEvent)
            is MultiViewModel.SubmitField -> bindSubmitField(emitEvent)
        }
    }

    private fun bindSubmitField(emitEvent: (FormEvent)-> Unit) {
        (binding as SubmitFormViewBinding).apply {
            submitButton.setOnClickListener {
                emitEvent(FormEvent.SubmitEvent)
            }
        }
    }

    private fun bindSpinner(model: MultiViewModel.SpinnerField, emitFormEvent: (FormEvent)-> Unit) {
        (binding as ListInputViewBinding).apply {

            //todo rework, make beauty )
            val valuesList = model.values?.values?.toList()
            //adapter
            ArrayAdapter(
                binding.root.context,
                R.layout.simple_spinner_item,
                requireNotNull(valuesList)
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

            val onItemSelectedListener =
                object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("SPINNER", "$parent $position $id")
                        val valueString = valuesList[position]
                        //todo check this KEY?
                        val resultString = model.values.keys.find {
                            model.values[it] == valueString
                        }

                        emitFormEvent(FormEvent.FieldEvent(
                            fieldName = model.name,
                            text = resultString ?: "v1"
                        ))
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

            spinner.apply {
                setSelection(0)
            }
            spinner.onItemSelectedListener = onItemSelectedListener
        }
    }

    private fun bindNumberField(model: MultiViewModel.NumberField, emitEvent: (FormEvent)-> Unit) {
        (binding as NumberInputViewBinding).apply {
            binding.numberInputEditText.addTextChangedListener{
                emitEvent(FormEvent.FieldEvent(
                    fieldName = model.name,
                    text = it.toString()
                ))
            }
        }

    }

    private fun bindTextField(model: MultiViewModel.TextField, emitEvent: (FormEvent)-> Unit) {
        (binding as TextInputViewBinding).apply {
            binding.textInputEditText.addTextChangedListener{
                emitEvent(FormEvent.FieldEvent(
                    fieldName = model.name,
                    text = it.toString()
                ))
            }
        }
    }

    private fun bindName(model: MultiViewModel.Name) {
        (binding as NameViewBinding).apply {
            binding.name.text = model.name
        }
    }
}