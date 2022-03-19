package com.bondidos.clevertecfinal.ui.form_fragment.formViewModel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bondidos.clevertecfinal.R
import com.bondidos.clevertecfinal.databinding.FormFragmentBinding
import com.bondidos.clevertecfinal.ui.di.appComponent
import com.bondidos.clevertecfinal.ui.form_fragment.FormViewModel
import com.bondidos.clevertecfinal.ui.form_fragment.adapter.FormAdapter
import com.bondidos.clevertecfinal.ui.uiState.State
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FormFragment : Fragment(R.layout.form_fragment) {

    private val binding by viewBinding(FormFragmentBinding::bind)

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var factory: FormFactory
    private val viewModel: FormViewModel by viewModels { factory }
    private val formAdapter: FormAdapter by lazy { FormAdapter(viewModel::emitEvent) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initDataListening()
    }

    private fun initDataListening() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state ->
                when (state) {
                    is State.Loading -> binding.progressBar.isVisible = true
                    is State.Success -> {
                        formAdapter.setData(state.data.fields)
                        activity?.title = state.data.title
                        glide.load(state.data.image).into(binding.logoImage)
                        binding.progressBar.isVisible = false
                    }
                    is State.Posted -> {
                        //todo Результат операции отправки данных на сервер отображается в диалоге.
                        Toast.makeText(requireContext(),state.message,Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                    }
                    is State.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recycler.apply {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = formAdapter
        }
    }
}