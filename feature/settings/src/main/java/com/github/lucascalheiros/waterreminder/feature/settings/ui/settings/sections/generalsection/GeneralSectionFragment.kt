package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.format.localizedName
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.SettingsSectionGeneralBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs.createDailyWaterIntakeInputDialog
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showMeasureSystemMenu
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showThemeMenu
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import org.koin.androidx.viewmodel.ext.android.viewModel


class GeneralSectionFragment : BaseFragment<GeneralSectionPresenter, GeneralSectionContract.View>(),
    GeneralSectionContract.View {

    override val presenter: GeneralSectionPresenter by viewModel()

    override val viewContract: GeneralSectionContract.View = this

    private var binding: SettingsSectionGeneralBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = SettingsSectionGeneralBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun SettingsSectionGeneralBinding.setupUI() {
        llContainer.clipToOutline = true
        setupListeners()
    }

    private fun SettingsSectionGeneralBinding.setupListeners() {
        llDailyIntake.setOnClickListener {
            presenter.onDailyWaterIntakeOptionClick()
        }
        llMeasureSystem.setOnClickListener { view ->
            view.showMeasureSystemMenu {
                presenter.onMeasureSystemSelected(it)
            }
        }
        llThemeColor.setOnClickListener { view ->
            view.showThemeMenu {
                presenter.onThemeSelected(it)
            }
        }
    }

    override fun setDailyWaterIntake(volume: MeasureSystemVolume) {
        binding?.tvDailyWaterIntakeValue?.text =
            volume.shortValueAndUnitFormatted(requireContext())
    }

    override fun setMeasureSystemUnit(unit: MeasureSystemVolumeUnit) {
        binding?.tvMeasureSystemValue?.text = unit.localizedName(requireContext())
    }

    override fun setTheme(theme: AppTheme) {
        binding?.tvThemeValue?.text = theme.name
    }

    override fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit) {
        context?.createDailyWaterIntakeInputDialog(unit) {
            presenter.onDailyWaterIntakeChanged(it)
        }?.show()
    }
}
