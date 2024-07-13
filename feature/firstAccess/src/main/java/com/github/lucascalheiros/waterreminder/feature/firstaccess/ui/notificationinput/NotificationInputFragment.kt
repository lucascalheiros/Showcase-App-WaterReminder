package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.notificationinput

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentNotificationInputBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.delayedFadeIn
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NotificationInputFragment :
    BaseFragment<NotificationInputPresenter, NotificationInputContract.View>(),
    NotificationInputContract.View {

    override val presenter: NotificationInputPresenter by viewModel()

    override val viewContract: NotificationInputContract.View = this

    private var binding: FragmentNotificationInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNotificationInputBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
        clContent.alpha = 0f
        llNotificationSwitch.alpha = 0f
        setupListeners()
    }.root

    override fun onResume() {
        super.onResume()
        binding?.root?.delayedFadeIn()
        binding?.clContent?.delayedFadeIn(2_000)
        binding?.llNotificationSwitch?.delayedFadeIn(2_000)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun updateStartTime(localTime: LocalTime) {
        binding?.tvStartValue?.text = localTime.toJavaLocalTime().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        )
    }

    override fun updateEndTime(localTime: LocalTime) {
        binding?.tvStopValue?.text = localTime.toJavaLocalTime().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        )
    }

    override fun updateFrequency(localTime: LocalTime) {
        val hourString = resources.getQuantityString(
            R.plurals.notification_frequency_hours,
            localTime.hour,
            localTime.hour
        )
        val minuteString = resources.getQuantityString(
            R.plurals.notification_frequency_minutes,
            localTime.minute,
            localTime.minute
        )
        binding?.tvFrequencyValue?.text = when {
            localTime.hour == 0 -> minuteString
            localTime.minute == 0 -> hourString
            else -> getString(R.string.notification_frequency_connective, hourString, minuteString)
        }
    }

    override fun updateDisabledSwitch(state: Boolean) {
        binding?.notificationDisabledSwitch?.isChecked = state
    }

    override fun showTimePicker(timePickerRequest: TimePickerRequest) {
        val time = timePickerRequest.localTime
        val timeFormat =
            if (DateFormat.is24HourFormat(context)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setInputMode(INPUT_MODE_KEYBOARD)
            .setTimeFormat(timeFormat)
            .setHour(time.hour)
            .setMinute(time.minute)
            .build()
        picker.addOnPositiveButtonClickListener {
            when (timePickerRequest) {
                is TimePickerRequest.End -> {
                    presenter.setEndTime(LocalTime(picker.hour, picker.minute))
                }
                is TimePickerRequest.Frequency -> {
                    presenter.setFrequency(LocalTime(picker.hour, picker.minute))
                }
                is TimePickerRequest.Start -> {
                    presenter.setStartTime(LocalTime(picker.hour, picker.minute))
                }
            }
        }
        picker.show(childFragmentManager, null)
    }

    private fun FragmentNotificationInputBinding.setupListeners() {
        cvStart.setOnClickListener {
            presenter.onStartTimeClick()
        }
        cvStop.setOnClickListener {
            presenter.onStopTimeClick()
        }
        cvFrequency.setOnClickListener {
            presenter.onFrequencyClick()
        }
        notificationDisabledSwitch.setOnClickListener {
            presenter.onNotificationDisableSwitch(notificationDisabledSwitch.isChecked)
        }
    }
}

