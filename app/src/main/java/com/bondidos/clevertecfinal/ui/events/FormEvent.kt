package com.bondidos.clevertecfinal.ui.events

sealed class FormEvent {
    data class FieldEvent(
        val fieldName: String,
        val text: String
        ): FormEvent()
    object SubmitEvent: FormEvent()
}