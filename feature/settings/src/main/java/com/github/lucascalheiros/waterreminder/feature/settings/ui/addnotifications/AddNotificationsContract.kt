package com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications

import kotlinx.datetime.LocalTime

interface AddNotificationsContract {
    interface View {
        fun setAddNotificationData(data: AddNotificationData)
        fun dismissBottomSheet()
        fun showTimePicker(timePickerRequest: TimePickerRequest)
    }

    interface Presenter {
        fun onStartTimeClick()
        fun onStopTimeClick()
        fun onPeriodTimeClick()
        fun onSingleTimeClick()
        fun onSetStartTime(time: LocalTime)
        fun onSetStopTime(time: LocalTime)
        fun onSetPeriodTime(time: LocalTime)
        fun onSetSingleTime(time: LocalTime)
        fun onSetDeleteOthers(value: Boolean)
        fun onConfirm()
        fun onCancel()
        fun onSetInputMode(mode: AddNotificationInputMode)
    }
}

enum class AddNotificationInputMode {
    Single,
    Multiple
}

sealed interface AddNotificationData {
    data class Single(val time: LocalTime): AddNotificationData

    data class Multiple(
        val startTime: LocalTime,
        val stopTime: LocalTime,
        val periodTime: LocalTime,
        val deleteOthersState: Boolean
    ): AddNotificationData
}

sealed class TimePickerRequest {
    abstract val time: LocalTime
    data class Single(override val time: LocalTime) : TimePickerRequest()
    data class Start(override val time: LocalTime) : TimePickerRequest()
    data class Stop(override val time: LocalTime) : TimePickerRequest()
    data class Period(override val time: LocalTime) : TimePickerRequest()
}