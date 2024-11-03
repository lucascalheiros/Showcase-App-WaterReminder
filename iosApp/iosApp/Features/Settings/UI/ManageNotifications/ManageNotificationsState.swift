//
//  ManageNotificationsState.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

struct ManageNotificationsState {
    var notificationInfoList: [NotificationInfo] = []
    private var _isInSelectionMode: Bool = false
    var isInSelectionMode: Bool {
        get {
            _isInSelectionMode && !isEmpty
        }
        set {
            _isInSelectionMode = newValue && !isEmpty
        }
    }
    var showAddNotificationsSheet: Bool = false
    var showWeekDayPickerForSelection: Bool = false
    var popoverData: WeekDayPopoverData?
    var selectionMap: [Int32:Bool] = [:]
    var allSelected: Bool {
        notificationInfoList.allSatisfy { selectionMap[$0.id] == true }
    }
    var isEmpty: Bool {
        notificationInfoList.isEmpty
    }
}
