package com.github.lucascalheiros.waterreminder.common.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewFloatingTittleBinding

class FloatingTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewFloatingTittleBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private val hiddenTranslation = resources.getDimension(R.dimen.floating_title_default_translation)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingTitleView)
        binding.tvFloatingTitle.text = typedArray.getString(R.styleable.FloatingTitleView_title)
        typedArray.recycle()
    }

    fun show() {
        ObjectAnimator.ofFloat(binding.flFloatingTitle, View.TRANSLATION_Y, 0f).apply {
            duration = SHOW_HIDE_ANIMATION_DURATION
        }.start()
    }

    fun hide() {
        ObjectAnimator.ofFloat(binding.flFloatingTitle, View.TRANSLATION_Y, hiddenTranslation).apply {
            duration = SHOW_HIDE_ANIMATION_DURATION
        }.start()
    }

    companion object {
        private const val SHOW_HIDE_ANIMATION_DURATION = 200L
    }

}