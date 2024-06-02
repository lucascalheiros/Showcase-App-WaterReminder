package com.github.lucascalheiros.waterreminder.common.appcore.savedstatehandleproperty

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import java.io.Serializable

class SavedStateHandleProperty<T>(
    private val state: SavedStateHandle,
    private val key: String,
    private val defaultValue: T?
) where T : Serializable {
    val stateFlow: StateFlow<T?>
        get() = state.getStateFlow(key, defaultValue)

    val value: T?
        get() = state[key]

    fun set(value: T?) {
        state[key] = value
    }

    companion object {
        fun <T> SavedStateHandle.savedStateProperty(
            key: String,
            defaultValue: T?
        ): SavedStateHandleProperty<T> where T : Serializable {
            return SavedStateHandleProperty(this, key, defaultValue)
        }
    }
}