package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import android.os.Bundle
import android.os.Parcelable
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentManageNotificationsBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications.AddNotificationsBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSectionAdapter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.dialogs.notificationWeekDaysPicker
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.menus.showManageNotificationsMenu
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ManageNotificationsFragment :
    BaseFragment<ManageNotificationsPresenter, ManageNotificationsContract.View>(),
    ManageNotificationsContract.View {

    override val presenter: ManageNotificationsPresenter by viewModel()

    override val viewContract: ManageNotificationsContract.View = this

    private var binding: FragmentManageNotificationsBinding? = null

    private var recyclerRestoredViewState: Parcelable? = null

    private val notificationTimeSectionAdapter by lazy {
        NotificationTimeSectionAdapter().apply {
            onRemoveScheduleClick = {
                presenter.onRemoveScheduleClick(it)
            }
            onNotificationDaysClick = {
                presenter.onNotificationDaysClick(it)
            }
        }
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
        rvManageNotifications.adapter = notificationTimeSectionAdapter
        setupListeners()
        setupContentInsets()
    }.root

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            RESTORE_RECYCLER_VIEW_STATE,
            binding?.rvManageNotifications?.layoutManager?.onSaveInstanceState()
        )
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelable<Parcelable>(RESTORE_RECYCLER_VIEW_STATE)?.let {
            recyclerRestoredViewState = it
        }
    }

    override fun updateSectionsData(data: ManageNotificationSectionsData) {
        binding?.tvNoNotifications?.isVisible = data.notificationTimeSection.isEmpty()
        notificationTimeSectionAdapter.submitList(data.notificationTimeSection)
        restoreRecyclerViewState()
    }

    override fun showNotificationWeekDaysPicker(dayTime: DayTime, selectedDays: List<WeekDay>) {
        context?.notificationWeekDaysPicker(
            getString(R.string.notification_days_picker_title, dayTime.formatShort()),
            selectedDays
        ) {
            presenter.onNotificationWeekDaysChange(dayTime, it)
        }?.show()
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
        ibMore.setOnClickListener {
            it.showManageNotificationsMenu {

            }
        }
        btnAddNotification.setOnClickListener {
            AddNotificationsBottomSheetFragment().show(childFragmentManager, null)
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

    companion object {
        private const val RESTORE_RECYCLER_VIEW_STATE = "RESTORE_RECYCLER_VIEW_STATE"

        private fun DayTime.formatShort(): String {
            return LocalTime(hour, minute).toJavaLocalTime().format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            )
        }
    }
}