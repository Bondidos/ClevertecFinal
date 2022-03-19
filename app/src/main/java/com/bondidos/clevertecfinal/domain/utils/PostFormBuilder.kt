package com.bondidos.clevertecfinal.domain.utils

import com.bondidos.clevertecfinal.ui.PostForm
import com.bondidos.clevertecfinal.ui.events.FormEvent

class PostFormBuilder {
    private val fields = mutableMapOf<String,String>()

    fun onFieldEvent(event: FormEvent.FieldEvent) {
        fields[event.fieldName] = event.text
    }
     fun build() = PostForm(fields)
}