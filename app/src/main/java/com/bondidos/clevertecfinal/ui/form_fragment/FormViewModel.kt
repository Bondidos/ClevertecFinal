package com.bondidos.clevertecfinal.ui.form_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.clevertecfinal.domain.utils.PostFormBuilder
import com.bondidos.clevertecfinal.domain.useCases.FetchFormUseCase
import com.bondidos.clevertecfinal.ui.events.FormEvent
import com.bondidos.clevertecfinal.ui.uiState.State
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TAG: String = "VM"

class FormViewModel @Inject constructor(
    private val fetchFormUseCase: FetchFormUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val uiState: StateFlow<State> = _uiState

    private val postForm: PostFormBuilder = PostFormBuilder()

    init {
        fetchForm()
    }

    fun emitEvent(event: FormEvent) {
        when(event){
            is FormEvent.FieldEvent -> postForm.onFieldEvent(event)
            is FormEvent.SubmitEvent -> {
                val form = postForm.build()

                Log.d(TAG,form.toString())
            }
        }
    }

    private fun fetchForm() {
        viewModelScope.launch {
            fetchFormUseCase()
                .onStart {
                    Log.d(TAG, "State.Loading")
                    _uiState.value = State.Loading
                }
                .catch {
                    Log.d(TAG, "State.Error")
                    _uiState.value = State.Error(
                        message = "Extract error message to r.strings"
                    )
                }
                .collect {
                    Log.d(TAG, "State.Collect")
                    Log.d(TAG, "$it")
                    _uiState.value = State.Success(it)
                }
        }
    }
}