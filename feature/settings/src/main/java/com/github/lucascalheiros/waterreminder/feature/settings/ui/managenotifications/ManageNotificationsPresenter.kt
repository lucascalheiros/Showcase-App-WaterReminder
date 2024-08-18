package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.flows.launchCollectLatest
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationRequest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.WeekdayState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManageNotificationsPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val getScheduledNotificationsUseCase: GetScheduledNotificationsUseCase,
    private val deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase,
    private val setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase,
) : BasePresenter<ManageNotificationsContract.View>(mainDispatcher),
    ManageNotificationsContract.Presenter {

    private val scheduledNotifications = getScheduledNotificationsUseCase()
    private val changeNotificationWeekDaysRequest =
        MutableStateFlow<NotificationWeekDaysRequest?>(null)
    private val isInSelectionMode = MutableStateFlow(false)
    private val selectionMap = MutableStateFlow<Map<DayTime, Boolean>>(mapOf())

    private val notificationsTimeSectionItems =
        combine(
            scheduledNotifications,
            isInSelectionMode,
            selectionMap
        ) { infoList, isInSelectionMode, selectionMap ->
            infoList.map { info ->
                NotificationTimeSection.Content.Item(
                    info.dayTime,
                    listOf(
                        WeekdayState(WeekDay.Sunday, info.weekState.sundayEnabled),
                        WeekdayState(WeekDay.Monday, info.weekState.mondayEnabled),
                        WeekdayState(WeekDay.Tuesday, info.weekState.tuesdayEnabled),
                        WeekdayState(WeekDay.Wednesday, info.weekState.wednesdayEnabled),
                        WeekdayState(WeekDay.Thursday, info.weekState.thursdayEnabled),
                        WeekdayState(WeekDay.Friday, info.weekState.fridayEnabled),
                        WeekdayState(WeekDay.Saturday, info.weekState.saturdayEnabled),
                    ),
                    isInSelectionMode,
                    selectionMap[info.dayTime] ?: false,
                )
            }

        }

    private val manageNotificationSectionsData = notificationsTimeSectionItems.map {
        ManageNotificationSectionsData(it)
    }

    private val isAllSelected = notificationsTimeSectionItems.map { items ->
        items.all { it.isSelected }
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
                    getScheduledNotificationsUseCase(dayTime).first().let { info ->
                        buildList {
                            when  {
                               info.weekState.sundayEnabled -> add(WeekDay.Sunday)
                               info.weekState.mondayEnabled -> add(WeekDay.Monday)
                               info.weekState.tuesdayEnabled -> add(WeekDay.Tuesday)
                               info.weekState.wednesdayEnabled -> add(WeekDay.Wednesday)
                               info.weekState.thursdayEnabled -> add(WeekDay.Thursday)
                               info.weekState.fridayEnabled -> add(WeekDay.Friday)
                               info.weekState.saturdayEnabled -> add(WeekDay.Saturday)
                            }
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
                val weekState = WeekState(
                    newWeekDays.contains(WeekDay.Sunday),
                    newWeekDays.contains(WeekDay.Monday),
                    newWeekDays.contains(WeekDay.Tuesday),
                    newWeekDays.contains(WeekDay.Wednesday),
                    newWeekDays.contains(WeekDay.Thursday),
                    newWeekDays.contains(WeekDay.Friday),
                    newWeekDays.contains(WeekDay.Saturday),
                )
                setWeekDayNotificationStateUseCase(dayTime, weekState)
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
                    val weekState = WeekState(
                        newWeekDays.contains(WeekDay.Sunday),
                        newWeekDays.contains(WeekDay.Monday),
                        newWeekDays.contains(WeekDay.Tuesday),
                        newWeekDays.contains(WeekDay.Wednesday),
                        newWeekDays.contains(WeekDay.Thursday),
                        newWeekDays.contains(WeekDay.Friday),
                        newWeekDays.contains(WeekDay.Saturday),
                    )
                    setWeekDayNotificationStateUseCase(dayTime, weekState)
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
                    notification.forEach { info ->
                        it[info.dayTime] = true
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
                    notification.forEach { info ->
                        it[info.dayTime] = false
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
