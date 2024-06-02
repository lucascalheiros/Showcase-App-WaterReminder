package com.github.lucascalheiros.waterreminder.common.ui.helpers

import android.view.View
import com.github.lucascalheiros.waterreminder.common.ui.R

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

fun View.setSurfaceListBackground(contextualPosition: ContextualPosition) {
    clipToOutline = true
    setBackgroundResource(contextualPosition.surfaceListBackground)
}

val ContextualPosition.surfaceListBackground: Int
    get() = when (this) {
        ContextualPosition.Top -> R.drawable.surface_list_top_round_shape
        ContextualPosition.Bottom -> R.drawable.surface_list_bottom_round_shape
        ContextualPosition.Middle -> R.drawable.surface_list_none_round_shape
        ContextualPosition.TopAndBottom -> R.drawable.surface_list_all_round_shape
    }

val ContextualPosition.showDivider: Boolean
    get() = this == ContextualPosition.Middle || this == ContextualPosition.Top
