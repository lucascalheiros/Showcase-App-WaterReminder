package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications

import android.os.Bundle
import android.os.Parcelable
import android.text.format.DateFormat
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ConcatAdapter
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.feature.settings.R
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.FragmentManageNotificationsBinding
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSectionAdapter
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.weekdaysswitch.WeekDaysSwitchSectionAdapter
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageNotificationsFragment :
    BaseFragment<ManageNotificationsPresenter, ManageNotificationsContract.View>(),
    ManageNotificationsContract.View {

    override val presenter: ManageNotificationsPresenter by viewModel()

    override val viewContract: ManageNotificationsContract.View = this

    private var binding: FragmentManageNotificationsBinding? = null

    private var recyclerRestoredViewState: Parcelable? = null

    private val weekDaysSwitchSectionAdapter by lazy {
        WeekDaysSwitchSectionAdapter().apply {
            onWeekDayNotificationStateChange = { weekDay, state ->
                presenter.onWeekDayNotificationStateChange(weekDay, state)
            }
        }
    }

    private val notificationTimeSectionAdapter by lazy {
        NotificationTimeSectionAdapter().apply {
            onAddScheduleClick = {
                showScheduleTimePicker()
            }
            onRemoveScheduleClick = {
                presenter.onRemoveScheduleClick(it)
            }

        }
    }

    private val manageNotificationSectionsConcatAdapter by lazy {
        ConcatAdapter(
            weekDaysSwitchSectionAdapter,
            notificationTimeSectionAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentManageNotificationsBinding.inflate(inflater, container, false).apply {
        binding = this
        rvManageNotifications.adapter = manageNotificationSectionsConcatAdapter
        setupListeners()
        setupContentInsets()
    }.root

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(RESTORE_RECYCLER_VIEW_STATE, binding?.rvManageNotifications?.layoutManager?.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelable<Parcelable>(RESTORE_RECYCLER_VIEW_STATE)?.let {
            recyclerRestoredViewState = it
        }
    }

    override fun updateSectionsData(data: ManageNotificationSectionsData) {
        weekDaysSwitchSectionAdapter.submitList(data.weekDaySection)
        notificationTimeSectionAdapter.submitList(data.notificationTimeSection)
        restoreRecyclerViewState()
    }

    private fun restoreRecyclerViewState() {
        recyclerRestoredViewState?.let {
            recyclerRestoredViewState = null
            binding?.rvManageNotifications?.layoutManager?.onRestoreInstanceState(it)
        }
    }

    private fun FragmentManageNotificationsBinding.setupListeners() {
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun FragmentManageNotificationsBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            windowInsets
        }
    }

    private fun showScheduleTimePicker() {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val timeFormat =
            if (DateFormat.is24HourFormat(context)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(timeFormat)
            .setHour(currentTime.hour)
            .setMinute(currentTime.minute)
            .setTitleText(R.string.select_notification_schedule)
            .build()
        picker.addOnPositiveButtonClickListener {
            presenter.onAddSchedule(DayTime(picker.hour, picker.minute))
        }
        picker.show(childFragmentManager, null)
    }

    companion object {
        private const val RESTORE_RECYCLER_VIEW_STATE = "RESTORE_RECYCLER_VIEW_STATE"
    }
}