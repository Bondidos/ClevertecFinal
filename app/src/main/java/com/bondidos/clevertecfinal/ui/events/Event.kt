package com.bondidos.clevertecfinal.ui.events

sealed class Event {
    class Submit(val form: String): Event()
}