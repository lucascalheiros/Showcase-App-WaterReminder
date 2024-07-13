package com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentAddNotificationsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddNotificationsBottomSheetFragment :
    BaseBottomSheetDialogFragment<AddNotificationsPresenter, AddNotificationsContract.View>(),
    AddNotificationsContract.View {

    override val presenter: AddNotificationsPresenter by viewModel()

    override val viewContract: AddNotificationsContract.View = this

    private var binding: FragmentAddNotificationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddNotificationsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun setAddNotificationData(data: AddNotificationData) {
        when (data) {
            is AddNotificationData.Multiple -> binding?.setNotificationData(data)

            is AddNotificationData.Single -> binding?.setNotificationData(data)
        }
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    private fun FragmentAddNotificationsBinding.setNotificationData(data: AddNotificationData.Multiple) {
        setStartTime(data.startTime)
        setStopTime(data.stopTime)
        setPeriodTime(data.periodTime)
        setDeleteOthersState(data.deleteOthersState)
        setMultipleSelectionVisible(true)
    }

    private fun FragmentAddNotificationsBinding.setNotificationData(data: AddNotificationData.Single) {
        setSingleTime(data.time)
        setMultipleSelectionVisible(false)
    }

    private fun FragmentAddNotificationsBinding.setMultipleSelectionVisible(isVisible: Boolean) {
        val transition = AutoTransition()
        transition.setDuration(500)
        transition.setOrdering(TransitionSet.ORDERING_TOGETHER)
        TransitionManager.beginDelayedTransition(llSettingsContainer, transition)
        llOptionStartAt.isVisible = isVisible
        llOptionStopAt.isVisible = isVisible
        llOptionPeriod.isVisible = isVisible
        llOptionDeleteOthers.isVisible = isVisible
        llOptionTime.isVisible = !isVisible
    }

    private fun FragmentAddNotificationsBinding.setupUI() {
        llSettingsContainer.clipToOutline = true
        llOptionStartAt.setOnClickListener {
            presenter.onStartTimeClick()
        }
        llOptionStopAt.setOnClickListener {
            presenter.onStopTimeClick()
        }
        llOptionPeriod.setOnClickListener {
            presenter.onPeriodTimeClick()
        }
        llOptionTime.setOnClickListener {
            presenter.onSingleTimeClick()
        }
        btnCancel.setOnClickListener {
            presenter.onCancel()
        }
        btnConfirm.setOnClickListener {
            presenter.onConfirm()
        }
        deleteOthersSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSetDeleteOthers(isChecked)
        }
        tabsSingleMultiple.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab) {
                    tabsSingleMultiple.getTabAt(0) -> presenter.onSetInputMode(
                        AddNotificationInputMode.Single
                    )

                    tabsSingleMultiple.getTabAt(1) -> presenter.onSetInputMode(
                        AddNotificationInputMode.Multiple
                    )

                    else -> {}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun showTimePicker(timePickerRequest: TimePickerRequest) {
        val time = timePickerRequest.time
        val isPeriod = timePickerRequest is TimePickerRequest.Period
        val timeFormat =
            if (DateFormat.is24HourFormat(context) || isPeriod) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(timeFormat)
            .setHour(time.hour)
            .setMinute(time.minute)
            .build()
        picker.addOnPositiveButtonClickListener {
            val newTime = LocalTime(picker.hour, picker.minute)
            when (timePickerRequest) {
                is TimePickerRequest.Stop -> presenter.onSetStopTime(newTime)

                is TimePickerRequest.Period -> presenter.onSetPeriodTime(newTime)

                is TimePickerRequest.Start -> presenter.onSetStartTime(newTime)

                is TimePickerRequest.Single -> presenter.onSetSingleTime(newTime)
            }
        }
        picker.show(childFragmentManager, null)
    }

    private fun FragmentAddNotificationsBinding.setSingleTime(time: LocalTime) {
        tvSingleTimeValue.text = time.shortTime()
    }

    private fun FragmentAddNotificationsBinding.setStartTime(time: LocalTime) {
        tvStartAtValue.text = time.shortTime()
    }

    private fun FragmentAddNotificationsBinding.setStopTime(time: LocalTime) {
        tvStopAtValue.text = time.shortTime()
    }

    private fun FragmentAddNotificationsBinding.setPeriodTime(time: LocalTime) {
        val hourString = resources.getQuantityString(
            R.plurals.notification_frequency_hours,
            time.hour,
            time.hour
        )
        val minuteString = resources.getQuantityString(
            R.plurals.notification_frequency_minutes,
            time.minute,
            time.minute
        )
        tvPeriodValue.text = when {
            time.hour == 0 -> minuteString
            time.minute == 0 -> hourString
            else -> getString(R.string.notification_frequency_connective, hourString, minuteString)
        }
    }

    private fun FragmentAddNotificationsBinding.setDeleteOthersState(value: Boolean) {
        deleteOthersSwitch.isChecked = value
    }

    private fun LocalTime.shortTime() = toJavaLocalTime().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    )
}