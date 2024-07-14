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
    private val changeNotificationWeekDaysRequest = MutableStateFlow<ChangeNotificationWeekDaysRequest?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notificationsTimeSectionItems =
        scheduledNotifications.flatMapLatest { dayTimeList ->
            if (dayTimeList.isEmpty()) {
                return@flatMapLatest flowOf(listOf())
            }
            combine(dayTimeList.map { dayTime ->
                getWeekDayNotificationStateUseCase(dayTime)
                    .map { states ->
                        NotificationTimeSection.Content.Item(
                            dayTime,
                            states.map { WeekdayState(it.weekDay, it.isEnabled) }
                        )
                    }
            }) {
                it.toList()
            }
        }.map {
            buildList {
                add(NotificationTimeSection.Title)
                addAll(it)
            }
        }

    private val manageNotificationSectionsData = notificationsTimeSectionItems.map {
        ManageNotificationSectionsData(it)
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
                changeNotificationWeekDaysRequest.value = ChangeNotificationWeekDaysRequest(
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
                setWeekDayNotificationStateUseCase(dayTime, WeekDay.entries.map { WeekDayNotificationState(
                    it, newWeekDays.contains(it)
                ) })
            } catch (e: Exception) {
                logError("::onNotificationWeekDaysChange", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        manageNotificationSectionsData.launchCollectLatest(this) {
            view?.updateSectionsData(it)
        }
        changeNotificationWeekDaysRequest.filterNotNull().launchCollectLatest(this) {
            view?.showNotificationWeekDaysPicker(it.dayTime, it.currentWeekDays) ?: return@launchCollectLatest
            handleChangeNotificationRequest()
        }
    }

    private fun handleChangeNotificationRequest() {
        changeNotificationWeekDaysRequest.value = null
    }
}

private data class ChangeNotificationWeekDaysRequest(
    val dayTime: DayTime,
    val currentWeekDays: List<WeekDay>
)
