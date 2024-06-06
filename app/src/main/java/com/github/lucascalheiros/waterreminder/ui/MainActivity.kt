package com.github.lucascalheiros.waterreminder.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.github.lucascalheiros.waterreminder.databinding.ActivityMainBinding
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase
import com.github.lucascalheiros.waterreminder.notifications.createNotificationChannels
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationPermissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                setupNotifications()
            }
        }

    private val setupRemindNotificationsUseCase: SetupRemindNotificationsUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setupNotificationsAfterPermission()
        setupNavBar()
        splashEndAnimation()
    }

    private fun splashEndAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val scaleX = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.SCALE_X,
                    2f
                )
                val scaleY = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.SCALE_Y,
                    2f
                )
                val alpha = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    0f
                )
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

    private fun setupNavBar() {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
        window.navigationBarColor = typedValue.data
    }

    private fun setupNotificationsAfterPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionRequestResult.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            setupNotifications()
        }
    }

    private fun setupNotifications() {
        createNotificationChannels()
        lifecycleScope.launch {
            setupRemindNotificationsUseCase()
        }
    }
}