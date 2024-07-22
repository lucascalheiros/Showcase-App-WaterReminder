package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.flows.launchCollectLatest
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationRequest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.WeekdayState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManageNotificationsPresenter(
    mainDispatcher: CoroutineDispatcher,
    getScheduledNotificationsUseCase: GetScheduledNotificationsUseCase,
    private val deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase,
    private val getWeekDayNotificationStateUseCase: GetWeekDayNotificationStateUseCase,
    private val setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase,
) : BasePresenter<ManageNotificationsContract.View>(mainDispatcher),
    ManageNotificationsContract.Presenter {

    private val scheduledNotifications = getScheduledNotificationsUseCase()
    private val changeNotificationWeekDaysRequest =
        MutableStateFlow<NotificationWeekDaysRequest?>(null)
    private val isInSelectionMode = MutableStateFlow(false)
    private val selectionMap = MutableStateFlow<Map<DayTime, Boolean>>(mapOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notificationsTimeSectionItems =
        scheduledNotifications.flatMapLatest { dayTimeList ->
            if (dayTimeList.isEmpty()) {
                return@flatMapLatest flowOf(listOf())
            }
            combine(dayTimeList.map { dayTime ->
                combine(
                    getWeekDayNotificationStateUseCase(dayTime),
                    isInSelectionMode,
                    selectionMap
                ) { states, isInSelectionMode, selectionMap ->
                    NotificationTimeSection.Content.Item(
                        dayTime,
                        states.map { WeekdayState(it.weekDay, it.isEnabled) },
                        isInSelectionMode,
                        selectionMap[dayTime] ?: false,
                    )
                }
            }) {
                it.toList()
            }
        }

    private val manageNotificationSectionsData = notificationsTimeSectionItems.map {
        ManageNotificationSectionsData(it)
    }

    private val isAllSelected = notificationsTimeSectionItems.map {
        it.all { it.isSelected }
    }

    override fun onRemoveScheduleClick(dayTime: DayTime) {
        viewModelScope.launch {
            try {
                deleteScheduledNotificationUseCase(DeleteScheduledNotificationRequest.Single(dayTime))
            } catch (e: Exception) {
                logError("::onRemoveScheduleClick", e)
            }
        }
    }

    override fun onNotificationDaysClick(dayTime: DayTime) {
        viewModelScope.launch {
            try {
                changeNotificationWeekDaysRequest.value = NotificationWeekDaysRequest.Single(
                    dayTime,
                    getWeekDayNotificationStateUseCase(dayTime).first().mapNotNull {
                        if (it.isEnabled) {
                            it.weekDay
                        } else {
                            null
                        }
                    }
                )
            } catch (e: Exception) {
                logError("::onNotificationDaysClick", e)
            }
        }
    }

    override fun onNotificationWeekDaysChange(dayTime: DayTime, newWeekDays: List<WeekDay>) {
        viewModelScope.launch {
            try {
                setWeekDayNotificationStateUseCase(dayTime, WeekDay.entries.map {
                    WeekDayNotificationState(
                        it, newWeekDays.contains(it)
                    )
                })
            } catch (e: Exception) {
                logError("::onNotificationWeekDaysChange", e)
            }
        }
    }

    override fun onSelectedNotificationWeekDaysChange(newWeekDays: List<WeekDay>) {
        viewModelScope.launch {
            try {
                selectionMap.value.entries.forEach { (dayTime, state) ->
                    if (!state) {
                        return@forEach
                    }
                    setWeekDayNotificationStateUseCase(dayTime, WeekDay.entries.map {
                        WeekDayNotificationState(
                            it, newWeekDays.contains(it)
                        )
                    })
                }

            } catch (e: Exception) {
                logError("::onNotificationWeekDaysChange", e)
            }
        }
    }

    override fun onItemSelectionToggle(dayTime: DayTime) {
        isInSelectionMode.value = true
        selectionMap.update { map ->
            map.toMutableMap().also {
                it[dayTime] = !it.getOrDefault(dayTime, false)
            }
        }
    }

    override fun onCheckAllClick() {
        viewModelScope.launch {
            isInSelectionMode.value = true
            val notification = scheduledNotifications.first()
            selectionMap.update { map ->
                map.toMutableMap().also {
                    notification.forEach { dayTime ->
                        it[dayTime] = true
                    }
                }
            }
        }
    }

    override fun onUncheckAllClick() {
        viewModelScope.launch {
            val notification = scheduledNotifications.first()
            selectionMap.update { map ->
                map.toMutableMap().also {
                    notification.forEach { dayTime ->
                        it[dayTime] = false
                    }
                }
            }
        }
    }

    override fun onDeleteSelectedClick() {
        viewModelScope.launch {
            selectionMap.value.entries.forEach { (dayTime, state) ->
                if (state) {
                    deleteScheduledNotificationUseCase(
                        DeleteScheduledNotificationRequest.Single(
                            dayTime
                        )
                    )
                }
            }
        }
    }

    override fun onNotificationDaysSelectedClick() {
        changeNotificationWeekDaysRequest.value = NotificationWeekDaysRequest.Selected(
            WeekDay.entries.toList()
        )
    }

    override fun onCancelSelectionModeClick() {
        isInSelectionMode.value = false
        selectionMap.value = mapOf()
    }

    override fun CoroutineScope.scopedViewUpdate() {
        manageNotificationSectionsData.launchCollectLatest(this) {
            view?.updateSectionsData(it)
        }
        changeNotificationWeekDaysRequest.filterNotNull().launchCollectLatest(this) {
            view?.showNotificationWeekDaysPicker(it) ?: return@launchCollectLatest
            handleChangeNotificationRequest()
        }
        isInSelectionMode.launchCollectLatest(this) {
            view?.setSelectionModeUI(it)
        }
        isAllSelected.launchCollectLatest(this) {
            view?.setOptionCheckUncheckAllOption(it)
        }
    }

    private fun handleChangeNotificationRequest() {
        changeNotificationWeekDaysRequest.value = null
    }
}
