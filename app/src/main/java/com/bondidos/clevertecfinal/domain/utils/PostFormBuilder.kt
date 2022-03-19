package com.bondidos.clevertecfinal.domain.utils

import com.bondidos.clevertecfinal.domain.PostForm
import com.bondidos.clevertecfinal.ui.events.FormEvent

class PostFormBuilder {
    private val fields = mutableMapOf<String,String>()

    //TODO Числовые значения должны отправляться с точками в качестве десятичных разделителей.
    fun onFieldEvent(event: FormEvent.FieldEvent) {
        fields[event.fieldName] = event.text
    }
     fun build() = PostForm(fields)
}