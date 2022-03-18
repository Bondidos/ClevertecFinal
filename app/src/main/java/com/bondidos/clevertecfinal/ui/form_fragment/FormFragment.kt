package com.bondidos.clevertecfinal.ui.form_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bondidos.clevertecfinal.R
import com.bondidos.clevertecfinal.databinding.FormFragmentBinding
import com.bondidos.clevertecfinal.ui.di.appComponent
import com.bondidos.clevertecfinal.ui.uiState.State
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FormFragment : Fragment(R.layout.form_fragment) {

    private val binding by viewBinding(FormFragmentBinding::bind)

    @Inject
    lateinit var factory: FormFactory
    private val viewModel: FormViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }

    private fun initState(){
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect{ state ->
                when(state){
                    is State.Loading -> binding.progressBar.isVisible = true
                    is State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.text.text = state.data.toString()
                    }
                    is State.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(),state.message,Toast.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}