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
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentManageNotificationsBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications.AddNotificationsBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.settings.ui.dialogs.notificationWeekDaysPicker
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSectionAdapter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.menus.ManageNotificationsMenuOptions
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
            onItemSelectionToggle = {
                presenter.onItemSelectionToggle(it)
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

    override fun showNotificationWeekDaysPicker(request: NotificationWeekDaysRequest) {
        val title = when (request) {
            is NotificationWeekDaysRequest.Selected -> getString(R.string.notification_days_picker_title_standalone)
            is NotificationWeekDaysRequest.Single -> getString(
                R.string.notification_days_picker_title,
                request.dayTime.formatShort()
            )
        }
        context?.notificationWeekDaysPicker(
            title,
            request.selectedDays
        ) {
            when (request) {
                is NotificationWeekDaysRequest.Selected -> presenter.onSelectedNotificationWeekDaysChange(
                    it
                )

                is NotificationWeekDaysRequest.Single -> presenter.onNotificationWeekDaysChange(
                    request.dayTime,
                    it
                )
            }
        }?.show()
    }

    override fun setSelectionModeUI(isSelectionModeEnabled: Boolean) {
        with(binding ?: return) {
            val transition = Fade()
            transition.setDuration(500)
            TransitionManager.beginDelayedTransition(root, transition)
            cvSelectionMode.isVisible = isSelectionModeEnabled
            btnAddNotification.isVisible = !isSelectionModeEnabled
        }
    }

    override fun setOptionCheckUncheckAllOption(isAllChecked: Boolean) {
        with(binding ?: return) {
            val checkUncheckIconRes = if (isAllChecked)
               com.github.lucascalheiros.waterreminder.common.ui.R.drawable.ic_checkbox_blank
            else
               com.github.lucascalheiros.waterreminder.common.ui.R.drawable.ic_checkbox_select
            val checkUncheckStringRes = if (isAllChecked)
                R.string.selection_mode_option_none
            else
                R.string.selection_mode_option_all
            btnCheckUnCheck.icon = resources.getDrawable(checkUncheckIconRes, null)
            btnCheckUnCheck.text = resources.getString(checkUncheckStringRes)
            btnCheckUnCheck.setOnClickListener {
                if (isAllChecked) {
                    presenter.onUncheckAllClick()
                } else {
                    presenter.onCheckAllClick()
                }
            }
        }
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
        ibMore.setOnClickListener { view ->
            view.showManageNotificationsMenu {
                when (it) {
                    ManageNotificationsMenuOptions.SelectAll -> presenter.onCheckAllClick()
                }
            }
        }
        btnAddNotification.setOnClickListener {
            AddNotificationsBottomSheetFragment().show(childFragmentManager, null)
        }
        btnNotificationDays.setOnClickListener {
            presenter.onNotificationDaysSelectedClick()
        }
        btnCancel.setOnClickListener {
            presenter.onCancelSelectionModeClick()
        }
        btnDelete.setOnClickListener {
            presenter.onDeleteSelectedClick()
        }
        btnCheckUnCheck.setOnClickListener {
            presenter.onCheckAllClick()
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