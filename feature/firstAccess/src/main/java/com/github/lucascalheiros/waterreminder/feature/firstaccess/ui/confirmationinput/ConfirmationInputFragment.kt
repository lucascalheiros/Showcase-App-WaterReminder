package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentConfirmationInputBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput.dialogs.createVolumeInputDialog
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.delayedFadeIn
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.google.android.material.slider.Slider
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmationInputFragment :
    BaseFragment<ConfirmationInputPresenter, ConfirmationInputContract.View>(),
    ConfirmationInputContract.View {

    override val presenter: ConfirmationInputPresenter by viewModel()

    override val viewContract: ConfirmationInputContract.View = this

    private var binding: FragmentConfirmationInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentConfirmationInputBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
        llContent.alpha = 0f
        setupListeners()
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

    override fun updateVolumeUnit(volumeUnit: MeasureSystemVolumeUnit) {
        val btnId = when (volumeUnit) {
            MeasureSystemVolumeUnit.ML -> R.id.btnMl
            MeasureSystemVolumeUnit.OZ_UK -> R.id.btnOzUk
            MeasureSystemVolumeUnit.OZ_US -> R.id.btnOzUs
        }
        binding?.toggleButton?.check(btnId)
    }

    override fun updateVolume(volume: MeasureSystemVolume) {
        with(binding ?: return) {
            val volumeInMl = volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue().toFloat()
            intakeSlider.safeUpdate(volumeInMl)
            tvWaterIntakeValue.text = volume.shortValueAndUnitFormatted(requireContext())
        }
    }

    override fun showVolumeInputDialog(volumeUnit: MeasureSystemVolumeUnit) {
        context?.createVolumeInputDialog(volumeUnit) {
            presenter.onVolumeSelected(
                MeasureSystemVolume.create(it, volumeUnit).toUnit(MeasureSystemVolumeUnit.ML)
                    .intrinsicValue()
            )
        }?.show()
    }

    private fun Slider.safeUpdate(value: Float) {
        this.value = value.coerceIn(valueFrom, valueTo)
    }

    private fun FragmentConfirmationInputBinding.setupListeners() {
        cvWaterIntakeValue.setOnClickListener {
            presenter.onVolumeClick()
        }
        btnOzUs.setOnClickListener {
            presenter.onVolumeUnitSelected(MeasureSystemVolumeUnit.OZ_US)
        }
        btnOzUk.setOnClickListener {
            presenter.onVolumeUnitSelected(MeasureSystemVolumeUnit.OZ_UK)
        }
        btnMl.setOnClickListener {
            presenter.onVolumeUnitSelected(MeasureSystemVolumeUnit.ML)
        }
        intakeSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                presenter.onVolumeSelected(value.toDouble())
            }
        }
    }
}
