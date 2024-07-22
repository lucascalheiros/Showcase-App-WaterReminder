package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentTemperatureLevelInputBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.SelectableCardButtonBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.delayedFadeIn
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput.helper.descriptionRes
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput.helper.titleRes
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperature
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import org.koin.androidx.viewmodel.ext.android.viewModel

class TemperatureLevelInputFragment :
    BaseFragment<TemperatureLevelInputPresenter, TemperatureLevelInputContract.View>(),
    TemperatureLevelInputContract.View {

    override val presenter: TemperatureLevelInputPresenter by viewModel()

    override val viewContract: TemperatureLevelInputContract.View = this

    private var binding: FragmentTemperatureLevelInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTemperatureLevelInputBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
        llContent.alpha = 0f
        setupClickListener()
    }.root

    override fun onResume() {
        super.onResume()
        binding?.root?.delayedFadeIn()
        binding?.llContent?.delayedFadeIn(1_000)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun updateSelected(temperatureLevel: AmbienceTemperatureLevel) {
        binding?.updateSelection(temperatureLevel)
    }

    override fun updateTemperatureUnit(unit: MeasureSystemTemperatureUnit) {
        with(binding ?: return) {
            cardCold.setupText(AmbienceTemperatureLevel.Cold, unit)
            cardModerate.setupText(AmbienceTemperatureLevel.Moderate, unit)
            cardWarm.setupText(AmbienceTemperatureLevel.Warm, unit)
            cardHot.setupText(AmbienceTemperatureLevel.Hot, unit)
            when (unit) {
                MeasureSystemTemperatureUnit.Celsius -> R.id.btnCelsius
                MeasureSystemTemperatureUnit.Fahrenheit -> R.id.btnFahrenheit
            }.let {
                toggleButton.check(it)
            }
        }
    }

    private fun FragmentTemperatureLevelInputBinding.setupClickListener() {
        cardCold.cvSelectableCard.setOnClickListener {
            presenter.selectCard(AmbienceTemperatureLevel.Cold)
        }
        cardModerate.cvSelectableCard.setOnClickListener {
            presenter.selectCard(AmbienceTemperatureLevel.Moderate)
        }
        cardWarm.cvSelectableCard.setOnClickListener {
            presenter.selectCard(AmbienceTemperatureLevel.Warm)
        }
        cardHot.cvSelectableCard.setOnClickListener {
            presenter.selectCard(AmbienceTemperatureLevel.Hot)
        }
        btnFahrenheit.setOnClickListener {
            presenter.setTemperatureUnit(MeasureSystemTemperatureUnit.Fahrenheit)
        }
        btnCelsius.setOnClickListener {
            presenter.setTemperatureUnit(MeasureSystemTemperatureUnit.Celsius)
        }
    }

    private fun FragmentTemperatureLevelInputBinding.updateSelection(selectedLevel: AmbienceTemperatureLevel? = null) {
        cardCold.cvSelectableCard.isChecked = selectedLevel == AmbienceTemperatureLevel.Cold
        cardModerate.cvSelectableCard.isChecked = selectedLevel == AmbienceTemperatureLevel.Moderate
        cardWarm.cvSelectableCard.isChecked = selectedLevel == AmbienceTemperatureLevel.Warm
        cardHot.cvSelectableCard.isChecked = selectedLevel == AmbienceTemperatureLevel.Hot
    }

    private fun SelectableCardButtonBinding.setupText(
        temperatureLevel: AmbienceTemperatureLevel,
        unit: MeasureSystemTemperatureUnit
    ) {
        tvTitle.setText(temperatureLevel.titleRes())
        tvDescription.text = when (temperatureLevel) {
            AmbienceTemperatureLevel.Cold -> getString(
                temperatureLevel.descriptionRes(),
                MeasureSystemTemperature.create(20.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext())
            )
            AmbienceTemperatureLevel.Moderate -> getString(
                temperatureLevel.descriptionRes(),
                MeasureSystemTemperature.create(20.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext()),
                MeasureSystemTemperature.create(25.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext())
            )
            AmbienceTemperatureLevel.Warm -> getString(
                temperatureLevel.descriptionRes(),
                MeasureSystemTemperature.create(25.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext()),
                MeasureSystemTemperature.create(30.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext())
            )
            AmbienceTemperatureLevel.Hot -> getString(
                temperatureLevel.descriptionRes(),
                MeasureSystemTemperature.create(30.0, MeasureSystemTemperatureUnit.Celsius)
                    .toUnit(unit).shortValueAndUnitFormatted(requireContext())
            )
        }
    }

}