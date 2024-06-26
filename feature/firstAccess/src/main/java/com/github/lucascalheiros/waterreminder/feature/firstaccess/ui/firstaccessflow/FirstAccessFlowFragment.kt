package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.common.appcore.navigation.NavigationRoutes
import com.github.lucascalheiros.waterreminder.common.permissionmanager.canScheduleExactAlarms
import com.github.lucascalheiros.waterreminder.common.permissionmanager.hasNotificationPermission
import com.github.lucascalheiros.waterreminder.common.permissionmanager.openExactSchedulePermissionSettingIntent
import com.github.lucascalheiros.waterreminder.common.permissionmanager.showExactSchedulePermissionDialog
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentFirstAccessFlowBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow.adapters.FirstAccessFlowPageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FirstAccessFlowFragment :
    BaseFragment<FirstAccessFlowPresenter, FirstAccessFlowContract.View>(),
    FirstAccessFlowContract.View {

    override val presenter: FirstAccessFlowPresenter by viewModel()

    override val viewContract: FirstAccessFlowContract.View = this

    private var binding: FragmentFirstAccessFlowBinding? = null

    private val adapter by lazy { FirstAccessFlowPageAdapter(this) }

    private val notificationPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            askForExactSchedulePermissionIfNecessary()
        }

    private val exactSchedulerSettingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            presenter.onPermissionsHandled()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFirstAccessFlowBinding.inflate(inflater, container, false).apply {
        binding = this
        setupContentInsets()
        setupPager()
        setupListeners()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun requestPermissions() {
        askForNotificationPermissionIfNecessary()
    }

    override fun navigateToMainFlow() {
        findNavController().navigate(NavigationRoutes.MainFlow.uri)
    }

    override fun showConfirmationFailureToast() {
        Toast.makeText(context, R.string.confirmation_unknown_error, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("InlinedApi")
    private fun askForNotificationPermissionIfNecessary() {
        if (context?.hasNotificationPermission() == false) {
            notificationPermissionResult.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            askForExactSchedulePermissionIfNecessary()
        }
    }

    @SuppressLint("InlinedApi")
    private fun askForExactSchedulePermissionIfNecessary() {
        if (context?.canScheduleExactAlarms() == false) {
            context?.showExactSchedulePermissionDialog({
                exactSchedulerSettingResult.launch(openExactSchedulePermissionSettingIntent())
            }, {
                presenter.onPermissionsHandled()
            })
        } else {
            presenter.onPermissionsHandled()
        }
    }

    private fun FragmentFirstAccessFlowBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
                bottomMargin = insets.bottom
            }
            windowInsets
        }
    }

    private fun FragmentFirstAccessFlowBinding.setupPager() {
        vpFirstAccessFlow.adapter = adapter
        vpFirstAccessFlow.isUserInputEnabled = false
        vpFirstAccessFlow.offscreenPageLimit = 2
        vpFirstAccessFlow.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding?.btnNext?.setText(R.string.next)
                        binding?.btnPrev?.isVisible = false
                    }

                    adapter.itemCount - 1 -> {
                        binding?.btnNext?.setText(com.github.lucascalheiros.waterreminder.common.appcore.R.string.confirm)
                        binding?.btnPrev?.isVisible = true
                    }

                    else -> {
                        binding?.btnNext?.setText(R.string.next)
                        binding?.btnPrev?.isVisible = true
                    }
                }
            }
        })
    }

    private fun FragmentFirstAccessFlowBinding.setupListeners() {
        btnPrev.setOnClickListener {
            vpFirstAccessFlow.currentItem = (vpFirstAccessFlow.currentItem - 1).coerceAtLeast(0)
        }
        btnNext.setOnClickListener {
            if (vpFirstAccessFlow.currentItem == adapter.itemCount - 1) {
                presenter.onConfirmFirstAccessFlow()
            } else {
                vpFirstAccessFlow.currentItem =
                    (vpFirstAccessFlow.currentItem + 1).coerceAtMost(adapter.itemCount - 1)
            }
        }
    }

}

