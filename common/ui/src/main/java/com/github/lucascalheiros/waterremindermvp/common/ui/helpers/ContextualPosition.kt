package com.github.lucascalheiros.waterremindermvp.common.ui.helpers

enum class ContextualPosition {
    Top,
    Bottom,
    Middle,
    TopAndBottom
}

inline fun <reified T> List<Any>.getContextualPosition(position: Int): ContextualPosition {
    val prev = (position - 1).takeIf { it >= 0 }
        ?.let { getOrNull(it) } is T
    val next = (position + 1).takeIf { it < count() }
        ?.let { getOrNull(it) } is T
    return when {
        prev && next -> ContextualPosition.Middle
        prev -> ContextualPosition.Bottom
        next -> ContextualPosition.Top
        else -> ContextualPosition.TopAndBottom
    }
}