package com.github.lucascalheiros.waterremindermvp.data.themewrapper.models

enum class ThemeOptions(val value: Int) {
    Light(0),
    Dark(1),
    Auto(2);

    companion object {
        fun fromValue(value: Int): ThemeOptions {
            return entries.find { it.value == value } ?: Auto
        }
    }
}