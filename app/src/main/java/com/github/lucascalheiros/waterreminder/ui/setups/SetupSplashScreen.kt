package com.github.lucascalheiros.waterreminder.ui.setups

import android.animation.AnimatorSet
import android.os.Build
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.lucascalheiros.waterreminder.common.ui.animation.alphaTo
import com.github.lucascalheiros.waterreminder.common.ui.animation.scaleXTo
import com.github.lucascalheiros.waterreminder.common.ui.animation.scaleYTo

fun AppCompatActivity.setupSplashScreen() {
    installSplashScreen()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            with(splashScreenView) {
                val scaleX = scaleXTo(2f)
                val scaleY = scaleYTo(2f)
                val alpha = alphaTo(0f)
                AnimatorSet().apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500L
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    playTogether(scaleY, scaleX, alpha)
                }.start()
            }
        }
    }
}