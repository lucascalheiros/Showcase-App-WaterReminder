package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings

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
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.localizedName
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterremindermvp.feature.settings.R
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.FragmentSettingsBinding
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings.dialogs.createDailyWaterIntakeInputDialog
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings.menus.showMeasureSystemMenu
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings.menus.showThemeMenu
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
        setupListeners()
        setupContentInsets()
        setupTitleVisibilityWatcher()
    }

    private fun FragmentSettingsBinding.setupTitleVisibilityWatcher() {
        val scrollShowPoint =
            resources.getDimension(com.github.lucascalheiros.waterremindermvp.common.ui.R.dimen.floating_title_scroll_show_point)
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

}
