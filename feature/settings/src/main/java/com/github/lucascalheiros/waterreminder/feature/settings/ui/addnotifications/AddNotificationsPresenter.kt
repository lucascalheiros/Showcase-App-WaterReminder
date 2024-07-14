package com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.flows.launchCollectLatest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationRequest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AddNotificationsPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val createScheduleNotificationUseCase: CreateScheduleNotificationUseCase,
    private val deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase,
    private val setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase,
) : BasePresenter<AddNotificationsContract.View>(mainDispatcher),
    AddNotificationsContract.Presenter {

    private val showTimePickerRequest = MutableStateFlow<TimePickerRequest?>(null)
    private val dismissEvent = MutableStateFlow<Unit?>(null)
    private val singleTime = MutableStateFlow(nowTime())
    private val startTime = MutableStateFlow(LocalTime(8, 0))
    private val stopTime = MutableStateFlow(LocalTime(20, 0))
    private val periodTime = MutableStateFlow(LocalTime(2, 0))
    private val isDeleteAllEnabled = MutableStateFlow(false)
    private val inputMode = MutableStateFlow(AddNotificationInputMode.Single)
    private val weekDays = MutableStateFlow(WeekDay.entries.toList())
    private val changeNotificationWeekDaysRequest =
        MutableStateFlow<ChangeNotificationWeekDaysRequest?>(null)
    private val multipleData = combine(
        startTime,
        stopTime,
        periodTime,
        isDeleteAllEnabled,
        weekDays
    ) { startTime, stopTime, periodTime, isDeleteAllEnabled, weekDays ->
        AddNotificationData.Multiple(startTime, stopTime, periodTime, isDeleteAllEnabled, weekDays)
    }
    private val singleData = combine(singleTime, weekDays) { singleTime, weekDays ->
        AddNotificationData.Single(singleTime, weekDays)
    }
    private val data =
        combine(inputMode, singleData, multipleData) { inputMode, singleData, multipleData ->
            when (inputMode) {
                AddNotificationInputMode.Single -> singleData
                AddNotificationInputMode.Multiple -> multipleData
            }
        }

    override fun onStartTimeClick() {
        showTimePickerRequest.value = TimePickerRequest.Start(startTime.value)
    }

    override fun onStopTimeClick() {
        showTimePickerRequest.value = TimePickerRequest.Stop(stopTime.value)
    }

    override fun onPeriodTimeClick() {
        showTimePickerRequest.value = TimePickerRequest.Period(periodTime.value)
    }

    override fun onSingleTimeClick() {
        showTimePickerRequest.value = TimePickerRequest.Single(singleTime.value)
    }

    override fun onWeekDaysClick() {
        changeNotificationWeekDaysRequest.value = ChangeNotificationWeekDaysRequest(
            weekDays.value
        )
    }

    override fun onSetStartTime(time: LocalTime) {
        startTime.value = time
    }

    override fun onSetStopTime(time: LocalTime) {
        stopTime.value = time
    }

    override fun onSetPeriodTime(time: LocalTime) {
        periodTime.value = time
    }

    override fun onSetSingleTime(time: LocalTime) {
        singleTime.value = time
    }

    override fun onSetDeleteOthers(value: Boolean) {
        isDeleteAllEnabled.value = value
    }

    override fun onWeekDaysChange(selectedWeekDays: List<WeekDay>) {
        weekDays.value = selectedWeekDays
    }

    override fun onConfirm() {
        viewModelScope.launch {
            if (shouldDeleteExistingNotifications()) {
                deleteScheduledNotificationUseCase(DeleteScheduledNotificationRequest.All)
            }
            val weekDays = weekDays.value
            val weekDaysState = WeekDay.entries.map { WeekDayNotificationState(
                it, weekDays.contains(it)
            ) }
            notificationDayTimeFromInput().forEach {
                createScheduleNotificationUseCase(it)
                setWeekDayNotificationStateUseCase(it, weekDaysState)
            }
            emitDismissEvent()
        }
    }

    override fun onCancel() {
        emitDismissEvent()
    }

    override fun onSetInputMode(mode: AddNotificationInputMode) {
        inputMode.value = mode
    }

    override fun CoroutineScope.scopedViewUpdate() {
        dismissEvent.filterNotNull().launchCollectLatest(this) {
            view?.dismissBottomSheet() ?: return@launchCollectLatest
            handleDismissEvent()
        }
        showTimePickerRequest.filterNotNull().launchCollectLatest(this) {
            view?.showTimePicker(it) ?: return@launchCollectLatest
            handleShowTimePickerEvent()
        }
        data.launchCollectLatest(this) {
            view?.setAddNotificationData(it)
        }
        changeNotificationWeekDaysRequest.filterNotNull().launchCollectLatest(this) {
            view?.showWeekDaysPicker(it.currentWeekDays) ?: return@launchCollectLatest
            handleChangeWeekDaysEvent()
        }
    }

    private fun emitDismissEvent() {
        dismissEvent.value = Unit
    }

    private fun handleDismissEvent() {
        dismissEvent.value = null
    }

    private fun handleShowTimePickerEvent() {
        showTimePickerRequest.value = null
    }

    private fun handleChangeWeekDaysEvent() {
        changeNotificationWeekDaysRequest.value = null
    }

    private fun nowTime(): LocalTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    }

    private fun notificationDayTimeFromInput(): List<DayTime> {
        val inputMode = inputMode.value
        return when (inputMode) {
            AddNotificationInputMode.Single -> listOf(singleTime.value.toSecondOfDay() / 60)
            AddNotificationInputMode.Multiple -> {
                val dayInMinutes = 24 * 60
                val startMinute = startTime.value.toSecondOfDay() / 60
                val stopMinute = (stopTime.value.toSecondOfDay() / 60).let {
                    if (startMinute > it) {
                        it + dayInMinutes
                    } else {
                        it
                    }
                }
                val period = periodTime.value.toSecondOfDay() / 60
                val numberOfNotifications = (stopMinute - startMinute) / period
                (0..numberOfNotifications).map {
                    (startMinute + it * period) % dayInMinutes
                }
            }
        }.map { DayTime.fromDayMinutes(it) }
    }

    private fun shouldDeleteExistingNotifications(): Boolean {
        return inputMode.value == AddNotificationInputMode.Multiple && isDeleteAllEnabled.value
    }
}

private data class ChangeNotificationWeekDaysRequest(
    val currentWeekDays: List<WeekDay>
)


