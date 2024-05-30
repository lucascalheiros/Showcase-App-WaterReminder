package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.requests.AsyncRequest
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.weekdaysswitch.WeekDaySwitchSection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ManageNotificationsPresenter(
    mainDispatcher: CoroutineDispatcher,
    getScheduledNotificationsUseCase: GetScheduledNotificationsUseCase,
    private val createScheduleNotificationUseCase: CreateScheduleNotificationUseCase,
    private val deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase,
    getWeekDayNotificationStateUseCase: GetWeekDayNotificationStateUseCase,
    private val setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase,
) : BasePresenter<ManageNotificationsContract.View>(mainDispatcher),
    ManageNotificationsContract.Presenter {

    private val scheduledNotifications = getScheduledNotificationsUseCase(AsyncRequest.Continuous)

    private val notificationsTimeSectionItems = scheduledNotifications.map {
        buildList {
            add(NotificationTimeSection.Title)
            addAll(it.map {
                NotificationTimeSection.Content.Item(it)
            })
            add(NotificationTimeSection.Content.AddItem)
        }
    }

    private val weekDaySwitchSectionItems = getWeekDayNotificationStateUseCase(AsyncRequest.Continuous).map {
        buildList {
            add(WeekDaySwitchSection.Title)
            addAll(it.map {
                WeekDaySwitchSection.Item(it.weekDay, it.isEnabled)
            })
        }
    }

    private val manageNotificationSectionsData = combine(
        weekDaySwitchSectionItems,
        notificationsTimeSectionItems
    ) { weekDaySection, notificationTimeSection ->
        ManageNotificationSectionsData(weekDaySection, notificationTimeSection)
    }

    override fun onAddSchedule(dayTime: DayTime) {
        viewModelScope.launch {
            try {
                createScheduleNotificationUseCase(dayTime)
            } catch (e: Exception) {
                logError("::onAddScheduleClick", e)
            }
        }
    }

    override fun onRemoveScheduleClick(dayTime: DayTime) {
        viewModelScope.launch {
            try {
                deleteScheduledNotificationUseCase(dayTime)
            } catch (e: Exception) {
                logError("::onRemoveScheduleClick", e)
            }
        }
    }

    override fun onWeekDayNotificationStateChange(weekDay: WeekDay, state: Boolean) {
        viewModelScope.launch {
            try {
                setWeekDayNotificationStateUseCase(weekDay, state)
            } catch (e: Exception) {
                logError("::onWeekDayNotificationStateChange", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            manageNotificationSectionsData.collectLatest {
                view?.updateSectionsData(it)
            }
        }
    }
}