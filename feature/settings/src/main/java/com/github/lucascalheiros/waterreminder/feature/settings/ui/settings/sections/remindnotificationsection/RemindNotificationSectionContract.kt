package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.remindnotificationsection

interface RemindNotificationSectionContract {
    interface View {
        fun setNotificationSwitchState(state: Boolean)
        fun openManageNotifications()
        fun setEnableStateOfNonPermissionOptions(state: Boolean)
        fun setPermissionRequiredOptionVisible(state: Boolean)
    }

    interface Presenter {
        fun onNotificationEnableChanged(state: Boolean)
        fun onManageNotificationsClick()
        fun onNecessaryPermissionUpdate(hasPendingPermission: Boolean)
    }
}
