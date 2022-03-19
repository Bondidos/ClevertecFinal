package com.bondidos.clevertecfinal.ui.form_fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bondidos.clevertecfinal.databinding.*
import com.bondidos.clevertecfinal.domain.uiModels.MultiViewModel
import com.bondidos.clevertecfinal.ui.events.FormEvent
import java.lang.IllegalArgumentException

class FormAdapter(private val event: (FormEvent) -> Unit) : RecyclerView.Adapter<FormAdapterViewHolder>() {

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
        holder.bind(adapterData[position],event)
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

