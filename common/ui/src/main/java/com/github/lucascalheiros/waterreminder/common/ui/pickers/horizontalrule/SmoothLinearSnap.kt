package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import androidx.recyclerview.widget.LinearSnapHelper


class SmoothLinearSnap : LinearSnapHelper() {

    override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        return super.onFling((velocityX / 1.5).toInt(), (velocityY / 1.5).toInt())
    }

}
