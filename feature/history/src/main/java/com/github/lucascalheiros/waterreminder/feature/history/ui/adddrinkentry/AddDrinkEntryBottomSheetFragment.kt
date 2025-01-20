package com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.history.R
import com.github.lucascalheiros.waterreminder.feature.history.databinding.FragmentAddDrinkEntryBottomSheetBinding
import com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry.dialogs.createSelectWaterSourceDialog
import com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry.dialogs.createVolumeInputDialog
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddDrinkEntryBottomSheetFragment :
    BaseBottomSheetDialogFragment<AddDrinkEntryPresenter, AddDrinkEntryContract.View>(),
    AddDrinkEntryContract.View {

    override val presenter: AddDrinkEntryPresenter by viewModel()

    override val viewContract: AddDrinkEntryContract.View = this

    private var binding: FragmentAddDrinkEntryBottomSheetBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddDrinkEntryBottomSheetBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    private fun FragmentAddDrinkEntryBottomSheetBinding.setupUI() {
        llSettingsContainer.clipToOutline = true

        llOptionType.setOnClickListener {
            presenter.onSelectWaterSourceTypeOptionClick()
        }

        llOptionVolume.setOnClickListener {
            presenter.onVolumeOptionClick()
        }

        llOptionDate.setOnClickListener {
            presenter.onDateTimeClick()
        }

        btnCancel.setOnClickListener {
            presenter.onCancelClick()
        }

        btnConfirm.setOnClickListener {
            presenter.onConfirmClick()
        }
    }

    override fun showSelectWaterSourceDialog(waterSourceTypeList: List<WaterSourceType>) {
        context?.createSelectWaterSourceDialog(waterSourceTypeList) {
            presenter.onWaterSourceTypeSelected(it)
        }?.show()
    }

    override fun showVolumeInputDialog(unit: MeasureSystemVolumeUnit) {
        context?.createVolumeInputDialog(unit) {
            presenter.onVolumeSelected(it)
        }?.show()
    }

    override fun setSelectedVolume(volume: MeasureSystemVolume) {
        with(binding ?: return) {
            tvValueVolume.text = volume.shortValueAndUnitFormatted(requireContext())
        }
    }

    override fun setSelectedWaterSourceType(waterSourceType: WaterSourceType) {
        val color = waterSourceType.run {
            requireContext().getThemeAwareColor(
                lightColor,
                darkColor
            )
        }.toInt()
        with(binding ?: return) {
            tvValueType.text = waterSourceType.name
            tvValueVolume.setTextColor(color)
            tvValueType.setTextColor(color)
            tvValueDate.setTextColor(color)
        }
    }

    override fun setSelectedDateTime(dateTime: LocalDateTime) {
        with(binding ?: return) {
            val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            tvValueDate.text = dateTimeFormatter.format(dateTime.toJavaLocalDateTime())
        }
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    override fun showOperationErrorToast(event: AddDrinkEntryContract.ErrorEvent) {
        val textRes = when (event) {
            AddDrinkEntryContract.ErrorEvent.DataLoadingFailed -> R.string.data_load_failure
            AddDrinkEntryContract.ErrorEvent.SaveFailed -> R.string.save_failure
        }
        Toast.makeText(context, textRes, Toast.LENGTH_LONG).show()
    }

    override fun showDateTimeInputDialog(dateTime: LocalDateTime) {
        val timeFormat =
            if (DateFormat.is24HourFormat(context)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker = MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(timeFormat)
            .setHour(dateTime.hour)
            .setMinute(dateTime.minute)
            .build()
        MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .setSelection(dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds())
            .build()
            .apply {
                addOnPositiveButtonClickListener { selectedDate ->
                    val localDate = Instant.fromEpochMilliseconds(selectedDate).toLocalDateTime(
                        TimeZone.UTC).date
                    timePicker.apply {
                        addOnCancelListener {
                            presenter.onDateTimeChange(localDate.atTime(dateTime.time))
                        }
                        addOnPositiveButtonClickListener {
                            presenter.onDateTimeChange(localDate.atTime(timePicker.hour, timePicker.minute))
                        }
                    }.show(parentFragmentManager, null)

                }
            }
            .show(parentFragmentManager, null)
    }

    companion object {
        fun newInstance(): AddDrinkEntryBottomSheetFragment {
            return AddDrinkEntryBottomSheetFragment()
        }
    }
}