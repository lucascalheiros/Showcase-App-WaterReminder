package com.github.lucascalheiros.waterreminder.feature.settings.ui.unitselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentUnitSelectorBinding
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnitSelectorFragment :
    BaseBottomSheetDialogFragment<UnitSelectorPresenter, UnitSelectorContract.View>(),
    UnitSelectorContract.View {

    override val presenter: UnitSelectorPresenter by viewModel()

    override val viewContract: UnitSelectorContract.View = this

    private var binding: FragmentUnitSelectorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUnitSelectorBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
        presenter.loadData()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    fun FragmentUnitSelectorBinding.setupUI() {
        rgVolume.setOnCheckedChangeListener { _, checkedId ->
            presenter.onChangeVolumeUnit(checkedId.toVolumeUnit() ?: return@setOnCheckedChangeListener)
        }
        rgTemperature.setOnCheckedChangeListener { _, checkedId ->
            presenter.onChangeTemperatureUnit(checkedId.toTemperatureUnit() ?: return@setOnCheckedChangeListener)
        }
        rgWeight.setOnCheckedChangeListener { _, checkedId ->
            presenter.onChangeWeightUnit(checkedId.toWeightUnit() ?: return@setOnCheckedChangeListener)
        }
    }

    override fun setVolumeUnit(value: MeasureSystemVolumeUnit) {
        binding?.rgVolume?.check(value.toRadioId())
    }

    override fun setTemperatureUnit(value: MeasureSystemTemperatureUnit) {
        binding?.rgTemperature?.check(value.toRadioId())
    }

    override fun setWeightUnit(value: MeasureSystemWeightUnit) {
        binding?.rgWeight?.check(value.toRadioId())
    }

    private fun MeasureSystemVolumeUnit.toRadioId(): Int {
        return when (this) {
            MeasureSystemVolumeUnit.ML -> R.id.rbMl
            MeasureSystemVolumeUnit.OZ_UK -> R.id.rbOzUk
            MeasureSystemVolumeUnit.OZ_US -> R.id.rbOzUs
        }
    }

    private fun Int.toVolumeUnit(): MeasureSystemVolumeUnit? {
        return when (this) {
            R.id.rbMl -> MeasureSystemVolumeUnit.ML
            R.id.rbOzUk -> MeasureSystemVolumeUnit.OZ_UK
            R.id.rbOzUs -> MeasureSystemVolumeUnit.OZ_US
            else -> null
        }
    }

    private fun MeasureSystemWeightUnit.toRadioId(): Int {
        return when (this) {
            MeasureSystemWeightUnit.KILOGRAMS -> R.id.rbKg
            MeasureSystemWeightUnit.GRAMS -> R.id.rbKg
            MeasureSystemWeightUnit.POUNDS -> R.id.rbLbs
        }
    }

    private fun Int.toWeightUnit(): MeasureSystemWeightUnit? {
        return when (this) {
            R.id.rbKg -> MeasureSystemWeightUnit.KILOGRAMS
            R.id.rbLbs -> MeasureSystemWeightUnit.POUNDS
            else -> null
        }
    }


    private fun MeasureSystemTemperatureUnit.toRadioId(): Int {
        return when (this) {
            MeasureSystemTemperatureUnit.Celsius -> R.id.rbC
            MeasureSystemTemperatureUnit.Fahrenheit -> R.id.rbF
        }
    }

    private fun Int.toTemperatureUnit(): MeasureSystemTemperatureUnit? {
        return when (this) {
            R.id.rbC -> MeasureSystemTemperatureUnit.Celsius
            R.id.rbF -> MeasureSystemTemperatureUnit.Fahrenheit
            else -> null
        }
    }

    companion object {
        fun newInstance(): UnitSelectorFragment {
            return UnitSelectorFragment()
        }
    }
}