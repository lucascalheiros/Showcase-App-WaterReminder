package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.notificationinput

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetFirstAccessNotificationStateUseCase
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetNotificationFrequencyTimeUseCase
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetStartNotificationTimeUseCase
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetStopNotificationTimeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

class NotificationInputPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val getFirstAccessNotificationDataUseCase: GetFirstAccessNotificationDataUseCase,
    private val setStartNotificationTimeUseCase: SetStartNotificationTimeUseCase,
    private val setStopNotificationTimeUseCase: SetStopNotificationTimeUseCase,
    private val setFirstAccessNotificationStateUseCase: SetFirstAccessNotificationStateUseCase,
    private val setNotificationFrequencyTimeUseCase: SetNotificationFrequencyTimeUseCase
) : BasePresenter<NotificationInputContract.View>(mainDispatcher),
    NotificationInputContract.Presenter {

    private val firstAccessNotificationData = getFirstAccessNotificationDataUseCase()
    private val startTime = firstAccessNotificationData.map {
        it.startTime
    }.distinctUntilChanged()
    private val stopTime = firstAccessNotificationData.map {
        it.stopTime
    }.distinctUntilChanged()
    private val frequency = firstAccessNotificationData.map {
        it.frequencyTime
    }.distinctUntilChanged()
    private val shouldEnable = firstAccessNotificationData.map {
        it.shouldEnable
    }.distinctUntilChanged()
    private val timePickerRequest = MutableStateFlow<TimePickerRequest?>(null)

    override fun setStartTime(localTime: LocalTime) {
        viewModelScope.launch {
            setStartNotificationTimeUseCase(localTime)
        }
    }

    override fun setEndTime(localTime: LocalTime) {
        viewModelScope.launch {
            setStopNotificationTimeUseCase(localTime)
        }
    }

    override fun setFrequency(localTime: LocalTime) {
        viewModelScope.launch {
            setNotificationFrequencyTimeUseCase(localTime)
        }
    }

    override fun onStartTimeClick() {
        viewModelScope.launch {
            timePickerRequest.value =
                TimePickerRequest.Start(getFirstAccessNotificationDataUseCase().first().startTime)
        }
    }

    override fun onStopTimeClick() {
        viewModelScope.launch {
            timePickerRequest.value =
                TimePickerRequest.End(getFirstAccessNotificationDataUseCase().first().stopTime)
        }
    }

    override fun onFrequencyClick() {
        viewModelScope.launch {
            timePickerRequest.value =
                TimePickerRequest.Frequency(getFirstAccessNotificationDataUseCase().first().frequencyTime)
        }
    }

    override fun onNotificationDisableSwitch(value: Boolean) {
        viewModelScope.launch {
            setFirstAccessNotificationStateUseCase(!value)
        }
    }

    private fun handleTimePickerRequest() {
        timePickerRequest.value = null
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            startTime.collectLatest {
                view?.updateStartTime(it)
            }
        }
        launch {
            stopTime.collectLatest {
                view?.updateEndTime(it)
            }
        }
        launch {
            frequency.collectLatest {
                view?.updateFrequency(it)
            }
        }
        launch {
            shouldEnable.collectLatest {
                view?.updateDisabledSwitch(!it)
            }
        }
        launch {
            timePickerRequest.filterNotNull().collectLatest {
                view?.showTimePicker(it) ?: return@collectLatest
                handleTimePickerRequest()
            }
        }
    }

}