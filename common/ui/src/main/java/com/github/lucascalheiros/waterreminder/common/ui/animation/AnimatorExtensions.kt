package com.github.lucascalheiros.waterreminder.common.ui.animation

import android.animation.ObjectAnimator
import android.view.View

fun View.scaleXTo(vararg value: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(
        this,
        View.SCALE_X,
        *value
    )
}

fun View.scaleYTo(vararg value: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(
        this,
        View.SCALE_Y,
        *value
    )
}

fun View.alphaTo(vararg value: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(
        this,
        View.ALPHA,
        *value
    )
}