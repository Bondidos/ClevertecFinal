package com.bondidos.clevertecfinal.ui.form_fragment.formViewModel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bondidos.clevertecfinal.R
import com.bondidos.clevertecfinal.databinding.FormFragmentBinding
import com.bondidos.clevertecfinal.di.appComponent
import com.bondidos.clevertecfinal.ui.form_fragment.FormViewModel
import com.bondidos.clevertecfinal.ui.form_fragment.adapter.FormAdapter
import com.bondidos.clevertecfinal.ui.uiState.State
import com.bondidos.clevertecfinal.ui.dialog.PostDialog
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FormFragment : Fragment(R.layout.form_fragment) {

    @Inject
    lateinit var glide: RequestManager
    @Inject
    lateinit var factory: FormFactory
    private val binding by viewBinding(FormFragmentBinding::bind)
    private val viewModel: FormViewModel by viewModels { factory }
    private val formAdapter: FormAdapter by lazy { FormAdapter(viewModel::emitEvent) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initListeners()
        initDataListening()
    }

    private fun initListeners() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initDataListening() {
        // state
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state ->
                when (state) {
                    is State.Loading -> {
                        if(!binding.swipeContainer.isRefreshing)
                        binding.progressBar.isVisible = true
                    }
                    is State.Success -> {
                        //set list
                        formAdapter.setData(state.data.fields)
                        //set form name
                        activity?.title = state.data.title
                        //load logo
                        glide.load(state.data.image).into(binding.logoImage)

                        if(binding.swipeContainer.isRefreshing){
                            binding.swipeContainer.isRefreshing = false
                        } else
                            binding.progressBar.isVisible = false
                    }
                    is State.Error -> {
                        binding.swipeContainer.isRefreshing = false
                        binding.progressBar.isVisible = false
                    }
                    else -> Unit
                }
            }

        }
        //event
        lifecycleScope.launchWhenCreated {
            viewModel.uiEvent.collectLatest { message ->
                PostDialog
                    .newInstance(message)
                    .show(requireActivity().supportFragmentManager,"result")
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