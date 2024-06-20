package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.notificationinput

import kotlinx.datetime.LocalTime

interface NotificationInputContract {
    interface View {
        fun updateStartTime(localTime: LocalTime)
        fun updateEndTime(localTime: LocalTime)
        fun updateFrequency(localTime: LocalTime)
        fun updateDisabledSwitch(state: Boolean)
        fun showTimePicker(timePickerRequest: TimePickerRequest)
    }

    interface Presenter {
        fun setStartTime(localTime: LocalTime)
        fun setEndTime(localTime: LocalTime)
        fun setFrequency(localTime: LocalTime)
        fun onStartTimeClick()
        fun onStopTimeClick()
        fun onFrequencyClick()
        fun onNotificationDisableSwitch(value: Boolean)
    }
}

sealed class TimePickerRequest {
    abstract val localTime: LocalTime
    data class Start(override val localTime: LocalTime) : TimePickerRequest()
    data class End(override val localTime: LocalTime) : TimePickerRequest()
    data class Frequency(override val localTime: LocalTime) : TimePickerRequest()
}