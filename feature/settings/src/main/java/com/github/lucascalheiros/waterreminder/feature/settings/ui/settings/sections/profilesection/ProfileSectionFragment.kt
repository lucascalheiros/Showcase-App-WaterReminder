package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.profilesection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.SettingsSectionProfileBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers.displayText
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs.createSetCalculatedIntakeDialog
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs.createWeightInputDialog
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showActivityLevelMenu
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus.showAmbienceTemperatureMenu
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileSectionFragment : BaseFragment<ProfileSectionPresenter, ProfileSectionContract.View>(),
    ProfileSectionContract.View {

    override val presenter: ProfileSectionPresenter by viewModel()

    override val viewContract: ProfileSectionContract.View = this

    private var binding: SettingsSectionProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = SettingsSectionProfileBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun SettingsSectionProfileBinding.setupUI() {
        llContainer.clipToOutline = true
        setupListeners()
    }

    private fun SettingsSectionProfileBinding.setupListeners() {
        llWeight.setOnClickListener {
            presenter.onShowWeightInputDialog()
        }
        llActivity.setOnClickListener { view ->
            view.showActivityLevelMenu {
                presenter.onUserActivityLevelSet(it)
            }
        }
        llTemperature.setOnClickListener { view ->
            view.showAmbienceTemperatureMenu {
                presenter.onUserTemperatureLevelSet(it)
            }
        }
        llCalculatedIntake.setOnClickListener {
            context?.createSetCalculatedIntakeDialog {
                presenter.onSetCalculatedIntakeAsDailyIntake()
            }?.show()
        }
    }

    override fun setUserProfile(userProfile: UserProfile) {
        val context = context ?: return
        with(binding ?: return) {
            tvUserName.text = userProfile.name
            tvWeight.text = userProfile.weight.shortValueAndUnitFormatted(context)
            tvActivityLevel.text = userProfile.activityLevel.displayText(context)
            tvTemperatureLevel.text = userProfile.temperatureLevel.displayText(context)
        }
    }

    override fun setCalculatedIntake(measureSystemVolume: MeasureSystemVolume) {
        binding?.tvCalculatedIntake?.text =
            measureSystemVolume.shortValueAndUnitFormatted(context ?: return)
    }

    override fun showWeightInputDialog(weightUnit: MeasureSystemWeightUnit) {
        context?.createWeightInputDialog(weightUnit) {
            presenter.onUserWeightSet(MeasureSystemWeight.Companion.create(it, weightUnit))
        }?.show()
    }

}
