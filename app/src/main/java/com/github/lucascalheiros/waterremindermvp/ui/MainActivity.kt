package com.github.lucascalheiros.waterremindermvp.ui

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
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

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        setupNotificationsAfterPermission()
        setupBottomNavBar()
        setupEdgeToEdgeInsets()
    }

    private fun setupBottomNavBar() {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
        window.navigationBarColor = typedValue.data
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        binding.navView.setupWithNavController(navController.navController)
    }

    private fun setupEdgeToEdgeInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.navView) { view: View, insets: WindowInsetsCompat ->
            val bottomInset =
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            view.updateLayoutParams<LinearLayout.LayoutParams> {
                bottomMargin = bottomInset
            }
            insets
        }
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