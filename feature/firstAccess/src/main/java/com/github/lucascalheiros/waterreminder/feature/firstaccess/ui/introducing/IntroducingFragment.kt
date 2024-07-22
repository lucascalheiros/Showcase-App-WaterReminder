package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentIntroducingBinding


class IntroducingFragment : Fragment() {

    private var binding: FragmentIntroducingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentIntroducingBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
    }.root

    override fun onResume() {
        super.onResume()
        binding?.root?.delayedFadeIn()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}

fun View.delayedFadeIn(startDelay: Long = 250, duration: Long = 1_000) {
    ObjectAnimator.ofFloat(this, View.ALPHA, 1f).apply {
        this.startDelay = startDelay
        this.duration = duration
        start()
    }
}