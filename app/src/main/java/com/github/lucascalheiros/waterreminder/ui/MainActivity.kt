package com.github.lucascalheiros.waterreminder.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.lucascalheiros.waterreminder.databinding.ActivityMainBinding
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase
import com.github.lucascalheiros.waterreminder.notifications.createNotificationChannels
import com.github.lucascalheiros.waterreminder.ui.setups.setupEdgeToEdge
import com.github.lucascalheiros.waterreminder.ui.setups.setupOrientation
import com.github.lucascalheiros.waterreminder.ui.setups.setupSplashScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val notificationPermissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                setupNotifications()
            }
        }

    private val setupRemindNotificationsUseCase: SetupRemindNotificationsUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupOrientation()
        setupSplashScreen()
        setupEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        setupNotificationsAfterPermission()
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
