package com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.FragmentDrinkShortcutBottomSheetBinding
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class DrinkShortcutBottomSheetFragment :
    BaseBottomSheetDialogFragment<DrinkShortcutPresenter, DrinkShortcutContract.View>(),
    DrinkShortcutContract.View {

    override val presenter: DrinkShortcutPresenter by viewModel()
    override val viewContract: DrinkShortcutContract.View = this

    private var binding: FragmentDrinkShortcutBottomSheetBinding? = null

    private val volumeUnitArg: MeasureSystemVolumeUnit by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(VOLUME_UNIT, MeasureSystemVolumeUnit::class.java)
        } else {
            requireArguments().getSerializable(VOLUME_UNIT) as MeasureSystemVolumeUnit
        }!!
    }

    private val drinkNameArg: String by lazy {
        requireArguments().getString(DRINK_NAME).orEmpty()
    }

    private val waterSourceIdArg: Long by lazy {
        requireArguments().getLong(WATER_SOURCE_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize(waterSourceIdArg)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDrinkShortcutBottomSheetBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    private fun FragmentDrinkShortcutBottomSheetBinding.setupUI() {
        tvSheetTitle.text = drinkNameArg
        setupUIListeners()
        setupVolumePicker()
    }

    private fun FragmentDrinkShortcutBottomSheetBinding.setupUIListeners() {
        btnCancel.setOnClickListener {
            presenter.onCancelClick()
        }
        btnConfirm.setOnClickListener {
            presenter.onConfirmClick()
        }
        npVolume.setOnValueChangedListener { _, _, newVal ->
            presenter.onVolumeSelected(pickerValueToIntrinsicVolume(newVal))
        }
    }

    private fun pickerValueToIntrinsicVolume(value: Int): Double {
        return when (volumeUnitArg) {
            MeasureSystemVolumeUnit.ML -> value.toDouble() * 10
            MeasureSystemVolumeUnit.OZ_UK,
            MeasureSystemVolumeUnit.OZ_US -> value.toDouble() / 2
        }
    }

    private fun FragmentDrinkShortcutBottomSheetBinding.setupVolumePicker() {
        npVolume.displayedValues = (1..500).map {
            MeasureSystemVolume.create(pickerValueToIntrinsicVolume(it), volumeUnitArg)
                .shortValueAndUnitFormatted(requireContext())
        }.toTypedArray()
        npVolume.minValue = 1
        npVolume.maxValue = 500
    }

    private fun MeasureSystemVolume.toPickerValue(): Int {
        return when (volumeUnit()) {
            MeasureSystemVolumeUnit.ML -> (intrinsicValue() / 10).roundToInt()
            MeasureSystemVolumeUnit.OZ_UK -> (intrinsicValue() * 2).roundToInt()
            MeasureSystemVolumeUnit.OZ_US -> (intrinsicValue() * 2).roundToInt()
        }
    }

    override fun setSelectedVolume(volume: MeasureSystemVolume) {
        binding?.npVolume?.value = volume.toPickerValue()
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    override fun showOperationErrorToast(event: DrinkShortcutContract.ErrorEvent) {
        val textRes = when (event) {
            DrinkShortcutContract.ErrorEvent.DataLoadingFailed -> R.string.data_load_failure
            DrinkShortcutContract.ErrorEvent.SaveFailed -> R.string.save_failure
        }
        Toast.makeText(context, textRes, Toast.LENGTH_LONG).show()
    }

    override fun setVolumeShortcuts(value: DefaultVolumeShortcuts) {
        with(binding ?: return) {
            chipCupSmallest.text =
                value.smallest.shortValueAndUnitFormatted(requireContext())
            chipCupSmallest.setOnClickListener {
                presenter.onVolumeSelected(value.smallest.intrinsicValue())
            }
            chipCupSmall.text = value.small.shortValueAndUnitFormatted(requireContext())
            chipCupSmall.setOnClickListener {
                presenter.onVolumeSelected(value.small.intrinsicValue())
            }

            chipCupMedium.text = value.medium.shortValueAndUnitFormatted(requireContext())
            chipCupMedium.setOnClickListener {
                presenter.onVolumeSelected(value.medium.intrinsicValue())
            }

            chipCupLarge.text = value.large.shortValueAndUnitFormatted(requireContext())
            chipCupLarge.setOnClickListener {
                presenter.onVolumeSelected(value.large.intrinsicValue())
            }
        }
    }

    companion object {
        private const val WATER_SOURCE_ID = "WATER_SOURCE_ID"
        private const val DRINK_NAME = "DRINK_NAME"
        private const val VOLUME_UNIT = "VOLUME_UNIT"

        fun newInstance(
            waterSourceId: Long,
            drinkName: String,
            volumeUnit: MeasureSystemVolumeUnit
        ): DrinkShortcutBottomSheetFragment {
            return DrinkShortcutBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putLong(WATER_SOURCE_ID, waterSourceId)
                    putString(DRINK_NAME, drinkName)
                    putSerializable(VOLUME_UNIT, volumeUnit)
                }
            }
        }
    }
}
