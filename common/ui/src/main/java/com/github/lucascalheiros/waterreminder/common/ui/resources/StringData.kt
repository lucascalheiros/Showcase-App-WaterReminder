package com.github.lucascalheiros.waterreminder.common.ui.resources

import android.widget.TextView
import androidx.annotation.StringRes

sealed interface StringData {
    data class Raw(val value: String): StringData
    data class Resource(@StringRes val value: Int, val params: Array<String> = arrayOf()):
        StringData {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Resource

            if (value != other.value) return false
            if (!params.contentEquals(other.params)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = value
            result = 31 * result + params.contentHashCode()
            return result
        }
    }
}

fun TextView.setStringData(value: StringData) {
    text = when (value) {
        is StringData.Raw -> {
            value.value
        }

        is StringData.Resource -> {
            resources.getString(value.value, *value.params)
        }
    }
}
