//
//  ManageNotificationsIntent.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

enum ManageNotificationsIntent {
    case showAddNotifications
    case dismissAddNotifications
    case confirmAddNotifications(AddNotificationResult)
    case dismissWeekDayPicker
    case showWeekDayPicker(WeekDayPopoverData)
    case setWeekState(WeekDayPopoverData, WeekState)
    case setWeekStateForSelection(WeekState)
    case enableSelectionMode
    case disableSelectionMode
    case selectItem(NotificationInfo)
    case unselectItem(NotificationInfo)
    case selectAll
    case unselectAll
    case deleteSelected
    case showWeekDayPickerForSelected
    case toggleAllSelection
}
