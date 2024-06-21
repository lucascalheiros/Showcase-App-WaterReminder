package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.remindnotificationsection

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.common.permissionmanager.canScheduleExactAlarms
import com.github.lucascalheiros.waterreminder.common.permissionmanager.hasNotificationPermission
import com.github.lucascalheiros.waterreminder.common.permissionmanager.openExactSchedulePermissionSettingIntent
import com.github.lucascalheiros.waterreminder.common.permissionmanager.openNotificationPermissionSettingIntent
import com.github.lucascalheiros.waterreminder.common.permissionmanager.shouldAskNotificationPermissionManually
import com.github.lucascalheiros.waterreminder.common.permissionmanager.showExactSchedulePermissionDialog
import com.github.lucascalheiros.waterreminder.common.permissionmanager.showNotificationPermissionDialog
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.SettingsSectionRemindNotificationsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RemindNotificationSectionFragment :
    BaseFragment<RemindNotificationSectionPresenter, RemindNotificationSectionContract.View>(),
    RemindNotificationSectionContract.View {

    override val presenter: RemindNotificationSectionPresenter by viewModel()

    override val viewContract: RemindNotificationSectionContract.View = this

    private var binding: SettingsSectionRemindNotificationsBinding? = null

    private val notificationPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            askForExactSchedulePermissionIfNecessary()
        }

    private val manualNotificationPermissionSettingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            askForExactSchedulePermissionIfNecessary()
        }

    private val exactSchedulerSettingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            updatePermissionState()
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = SettingsSectionRemindNotificationsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onResume() {
        super.onResume()
        updatePermissionState()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun updatePermissionState() {
        presenter.onNecessaryPermissionUpdate(!requireContext().run { canScheduleExactAlarms() && hasNotificationPermission() })
    }

    private fun SettingsSectionRemindNotificationsBinding.setupUI() {
        llContainer.clipToOutline = true
        setupListeners()
    }

    private fun SettingsSectionRemindNotificationsBinding.setupListeners() {
        switchNotificationEnabled.setOnClickListener {
            presenter.onNotificationEnableChanged(switchNotificationEnabled.isChecked)
        }
        llManageNotifications.setOnClickListener {
            openManageNotifications()
        }
        llNotificationPermissionDenied.setOnClickListener {
            askForNotificationPermissionIfNecessary()
        }
    }

    override fun setNotificationSwitchState(state: Boolean) {
        binding?.switchNotificationEnabled?.isChecked = state
    }

    override fun openManageNotifications() {
        val tvManageNotificationsOption =
            binding?.tvManageNotificationsOption ?: return
        val extras =
            FragmentNavigatorExtras(tvManageNotificationsOption to "manageNotificationsTitle")
        findNavController().navigate(
            R.id.action_settingsFragment_to_manageNotificationsFragment,
            null,
            null,
            extras
        )
    }

    override fun setEnableStateOfNonPermissionOptions(state: Boolean) {
        binding?.switchNotificationEnabled?.isEnabled = state
        binding?.tvNotificationEnabledOption?.isEnabled = state

        binding?.llManageNotifications?.isEnabled = state
    }

    override fun setPermissionRequiredOptionVisible(state: Boolean) {
        binding?.llNotificationPermissionDenied?.isVisible = state
    }

    @SuppressLint("InlinedApi")
    private fun askForNotificationPermissionIfNecessary() {
        if (activity?.shouldAskNotificationPermissionManually() == true) {
            context?.showNotificationPermissionDialog({
                manualNotificationPermissionSettingResult.launch(context?.openNotificationPermissionSettingIntent())
            }, {
                askForExactSchedulePermissionIfNecessary()
            })
        } else if (context?.hasNotificationPermission() == false) {
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
                updatePermissionState()
            })
        } else {
            updatePermissionState()
        }
    }

}
