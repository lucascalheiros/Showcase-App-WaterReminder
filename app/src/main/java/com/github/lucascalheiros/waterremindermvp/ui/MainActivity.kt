package com.github.lucascalheiros.waterremindermvp.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.github.lucascalheiros.waterremindermvp.R
import com.github.lucascalheiros.waterremindermvp.databinding.ActivityMainBinding
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase
import com.github.lucascalheiros.waterremindermvp.notifications.createNotificationChannels
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

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