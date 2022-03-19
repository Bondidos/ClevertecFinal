package com.bondidos.clevertecfinal.ui.form_fragment.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.bondidos.clevertecfinal.databinding.*
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel
import java.lang.IllegalArgumentException

class FormAdapter : RecyclerView.Adapter<FormAdapterViewHolder>() {

    private val adapterData = mutableListOf<MultiViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormAdapterViewHolder {
        val binding = when (viewType) {
            TYPE_NAME -> NameViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TYPE_INPUT_TEXT -> TextInputViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TYPE_INPUT_NUMBER -> NumberInputViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TYPE_INPUT_LIST -> ListInputViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TYPE_SUBMIT -> SubmitFormViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> throw IllegalArgumentException("Invalid type") // todo string extract
        }
        binding.root.layoutParams.apply {
            if (viewType == TYPE_SUBMIT &&
                this is StaggeredGridLayoutManager.LayoutParams
            ) {
                this.isFullSpan = true
            }
        }
        return FormAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FormAdapterViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is MultiViewModel.Name -> TYPE_NAME
            is MultiViewModel.TextField -> TYPE_INPUT_TEXT
            is MultiViewModel.NumberField -> TYPE_INPUT_NUMBER
            is MultiViewModel.SpinnerField -> TYPE_INPUT_LIST
            is MultiViewModel.SubmitField -> TYPE_SUBMIT
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MultiViewModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_NAME = 0
        const val TYPE_INPUT_TEXT = 1
        const val TYPE_INPUT_NUMBER = 2
        const val TYPE_INPUT_LIST = 3
        const val TYPE_SUBMIT = 4
    }
}

class FormAdapterViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MultiViewModel) {
        when (model) {
            is MultiViewModel.Name -> bindName(model)
            is MultiViewModel.TextField -> bindTextField(model)
            is MultiViewModel.NumberField -> bindNumberField(model)
            is MultiViewModel.SpinnerField -> bindSpinner(model)
            is MultiViewModel.SubmitField -> bindSubmitField(model)
        }
    }

    private fun bindSubmitField(model: MultiViewModel.SubmitField) {
        (binding as SubmitFormViewBinding).apply {
            submitButton.setOnClickListener { }
        }
    }

    private fun bindSpinner(model: MultiViewModel.SpinnerField) {
        (binding as ListInputViewBinding).apply {

            //todo rework, make beauty )
            val valuesList = model.values?.values?.toList()
            //adapter
            ArrayAdapter(
                binding.root.context,
                android.R.layout.simple_spinner_item,
                requireNotNull(valuesList)
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

            val onItemSelectedListener =
                object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("SPINNER", "$parent $position $id")
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

    private fun bindNumberField(model: MultiViewModel.NumberField) {
        (binding as NumberInputViewBinding).apply {
        }

    }

    private fun bindTextField(model: MultiViewModel.TextField) {
        (binding as TextInputViewBinding).apply {
        }
    }

    private fun bindName(model: MultiViewModel.Name) {
        (binding as NameViewBinding).apply {
            binding.name.text = model.name
        }
    }
}
