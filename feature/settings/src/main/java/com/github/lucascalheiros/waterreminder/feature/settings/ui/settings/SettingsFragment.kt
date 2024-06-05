package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.github.lucascalheiros.waterreminder.common.appcore.format.localizedName
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentSettingsBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs.createDailyWaterIntakeInputDialog
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showMeasureSystemMenu
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showThemeMenu
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment<SettingsPresenter, SettingsContract.View>(),
    SettingsContract.View {

    override val presenter: SettingsPresenter by viewModel()

    override val viewContract: SettingsContract.View = this

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun FragmentSettingsBinding.setupUI() {
        sectionGeneral.llContainer.clipToOutline = true
        sectionRemindNotifications.llContainer.clipToOutline = true
        sectionProfile.llContainer.clipToOutline = true
        setupListeners()
        setupContentInsets()
        setupTitleVisibilityWatcher()
    }

    private fun FragmentSettingsBinding.setupTitleVisibilityWatcher() {
        val scrollShowPoint =
            resources.getDimension(com.github.lucascalheiros.waterreminder.common.ui.R.dimen.floating_title_scroll_show_point)
        rootScroll.setOnScrollChangeListener { _, _, y, _, _ ->
            val showFloatingTitle = y > scrollShowPoint
            if (showFloatingTitle) {
                floatingTitleView.show()
            } else {
                floatingTitleView.hide()
            }
        }
    }

    private fun FragmentSettingsBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(rootScroll) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            floatingTitleView.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            windowInsets
        }
    }

    private fun FragmentSettingsBinding.setupListeners() {
        sectionGeneral.llDailyIntake.setOnClickListener {
            presenter.onDailyWaterIntakeOptionClick()
        }
        sectionGeneral.llMeasureSystem.setOnClickListener { view ->
            view.showMeasureSystemMenu {
                presenter.onMeasureSystemSelected(it)
            }
        }
        sectionGeneral.llThemeColor.setOnClickListener { view ->
            view.showThemeMenu {
                presenter.onThemeSelected(it)
            }
        }
        sectionRemindNotifications.switchNotificationEnabled.setOnClickListener {
            presenter.onNotificationEnableChanged(sectionRemindNotifications.switchNotificationEnabled.isChecked)
        }
        sectionRemindNotifications.llManageNotifications.setOnClickListener {
            openManageNotifications()
        }
    }

    override fun setDailyWaterIntake(volume: MeasureSystemVolume) {
        binding?.sectionGeneral?.tvDailyWaterIntakeValue?.text =
            volume.shortValueAndUnitFormatted(requireContext())
    }

    override fun setMeasureSystemUnit(unit: MeasureSystemUnit) {
        binding?.sectionGeneral?.tvMeasureSystemValue?.text = unit.localizedName(requireContext())
    }

    override fun setTheme(theme: AppTheme) {
        binding?.sectionGeneral?.tvThemeValue?.text = theme.name
    }

    override fun setNotificationEnabledState(state: Boolean) {
        binding?.sectionRemindNotifications?.switchNotificationEnabled?.isChecked = state
    }

    override fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit) {
        context?.createDailyWaterIntakeInputDialog(unit) {
            presenter.onDailyWaterIntakeChanged(it)
        }?.show()
    }

    override fun setUserProfile(userProfile: UserProfile) {
        val context = context ?: return
        with(binding?.sectionProfile ?: return) {
            tvUserName.text = userProfile.name
            tvWeight.text = userProfile.weight.shortValueAndUnitFormatted(context)
            tvActivityLevel.text = userProfile.activityLevelInWeekDays.let {
                getString(R.string.activity_level_days, it)
            }
            tvTemperatureLevel.text = userProfile.temperatureLevel.displayText(context)
        }
    }

    override fun setCalculatedIntake(measureSystemVolume: MeasureSystemVolume) {
        binding?.sectionProfile?.tvCalculatedIntake?.text =
            measureSystemVolume.shortValueAndUnitFormatted(context ?: return)
    }

    override fun openManageNotifications() {
        val tvManageNotificationsOption =
            binding?.sectionRemindNotifications?.tvManageNotificationsOption ?: return
        val extras =
            FragmentNavigatorExtras(tvManageNotificationsOption to "manageNotificationsTitle")
        findNavController().navigate(
            R.id.action_settingsFragment_to_manageNotificationsFragment,
            null,
            null,
            extras
        )
    }

    private fun AmbienceTemperatureLevel.displayText(context: Context): String {
        return when (this) {
            AmbienceTemperatureLevel.Cold -> R.string.temperature_level_cold
            AmbienceTemperatureLevel.Moderate -> R.string.temperature_level_moderate
            AmbienceTemperatureLevel.Warn -> R.string.temperature_level_warm
            AmbienceTemperatureLevel.Hot -> R.string.temperature_level_hot
        }.let {
            context.resources.getString(it)
        }
    }

}
