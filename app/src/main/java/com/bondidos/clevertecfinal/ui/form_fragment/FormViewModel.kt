package com.bondidos.clevertecfinal.ui.form_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.clevertecfinal.domain.constants.Constants.NETWORK_ERROR
import com.bondidos.clevertecfinal.domain.utils.PostFormBuilder
import com.bondidos.clevertecfinal.domain.useCases.FetchFormUseCase
import com.bondidos.clevertecfinal.domain.useCases.PostFormUseCase
import com.bondidos.clevertecfinal.ui.events.FormEvent
import com.bondidos.clevertecfinal.ui.uiState.State
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FormViewModel @Inject constructor(
    private val fetchFormUseCase: FetchFormUseCase,
    private val postFormUseCase: PostFormUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent.asSharedFlow()

    private val postForm: PostFormBuilder = PostFormBuilder()

    init { fetchForm() }

    fun emitEvent(event: FormEvent) {
        when (event) {
            is FormEvent.FieldEvent -> postForm.onFieldEvent(event)
            is FormEvent.SubmitEvent -> {
                val form = postForm.build()
                viewModelScope.launch {
                    postFormUseCase(form)
                        .catch { _uiEvent.emit(NETWORK_ERROR) }
                        .collect { postResult ->
                            _uiEvent.emit(postResult.result)
                        }
                }
            }
        }
    }

    private fun fetchForm() {
        viewModelScope.launch {
            fetchFormUseCase()
                .onStart { _uiState.value = State.Loading }
                .catch {
                    _uiState.value = State.Error
                    _uiEvent.emit(NETWORK_ERROR)
                }
                .collect { _uiState.value = State.Success(it) }
        }
    }

    fun refresh() {
        fetchForm()
    }
}