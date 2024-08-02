package com.github.lucascalheiros.waterreminder.common.appcore.savedstatehandleproperty

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serializable

interface SavedStateHandleProperty <T> {
    val flow: Flow<T?>
    val value: T?
    fun set(value: T?)
}

class SavedStateHandlePropertyKSerializable<T>(
    private val state: SavedStateHandle,
    private val key: String,
    private val defaultValue: T?,
    private val deserializer: (String) -> T?,
    private val serializer: (T) -> String,
): SavedStateHandleProperty<T> {
    override val flow: Flow<T?>
        get() = state.getStateFlow(key, defaultValue?.let { serializer(it) }).map { it?.let { it1 ->
            deserializer(
                it1
            )
        } }

    override val value: T?
        get() {
            val serialized: String? = state[key]
            return serialized?.let { deserializer(it) }
        }

    override fun set(value: T?) {
        state[key] = value?.let { serializer(it) }
    }

    companion object {
        inline fun <reified T> SavedStateHandle.savedStateKSerializableProperty(
            key: String,
            defaultValue: T?
        ): SavedStateHandleProperty<T> {
            return SavedStateHandlePropertyKSerializable(this, key, defaultValue, {
                it.let { it1 -> Json.decodeFromString<T>(it1) }
            }, {
                Json.encodeToString(it)
            })
        }
    }
}

class SavedStateHandlePropertySerializable<T>(
    private val state: SavedStateHandle,
    private val key: String,
    private val defaultValue: T?
): SavedStateHandleProperty<T>  where T : Serializable {
    override val flow: StateFlow<T?>
        get() = state.getStateFlow(key, defaultValue)

    override val value: T?
        get() = state[key]

    override fun set(value: T?) {
        state[key] = value
    }

    companion object {
        fun <T> SavedStateHandle.savedStateSerializableProperty(
            key: String,
            defaultValue: T?
        ): SavedStateHandleProperty<T> where T : Serializable {
            return SavedStateHandlePropertySerializable(this, key, defaultValue)
        }
    }
}
