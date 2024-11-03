//
//  SettingsStringResources.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum SettingsSR: String, StringResourcesProtocol {
    case settingsTitle
    case generalHeaderTitle
    case dailyWaterIntakeOption
    case unitsOption
    case themeOption
    case remindNotificationsHeaderTitle
    case notificationsEnabledOption
    case manageNotificationsOption
    case profileHeaderTitle
    case weightOption
    case activityLevelOption
    case temperatureLevelOption
    case calculatedIntakeOption
    case manageNotificationsTitle
    case everyDay
    case noNotification
    case weekend
    case mondayToFriday
    case sundayShort
    case mondayShort
    case tuesdayShort
    case wednesdayShort
    case thursdayShort
    case fridayShort
    case saturdayShort
    case addNotificationsTitle
    case addNotificationsCancel
    case addNotificationsConfirm
    case addNotificationsSingleNotificationTime
    case addNotificationsSingleNotificationDays
    case addNotificationsSingle
    case addNotificationsMultiple
    case addNotificationsMultipleStartAt
    case addNotificationsMultipleStoptAt
    case addNotificationsMultipleNotifyEach
    case addNotificationsMultipleNotificationDays
    case addNotificationsMultipleDeleteOthers
    case weekDaysAlertTitleForSelection
    case weekDaysAlertTitleForTime
    case weekDaysAlertCancel
    case weekDaysAlertConfirm
    case missingAlertPermission
    case missingAlertSoundPermission
    case alertPermissionDeniedTitle
    case alertPermissionDeniedDescription 
    case alertSoundPermissionDeniedTitle
    case alertSoundPermissionDeniedDescription
    case alertPermissionSettingsOption
    case themeOptionDark
    case themeOptionLight
    case themeOptionAuto
    case manageNotificationOptionsDays
    case manageNotificationOptionsDelete
    case manageNotificationOptionsCheckAll
    case manageNotificationOptionsUncheckAll
    case manageNotificationOptionsCancel
    case manageNotificationsSelectAll
    case manageNotificationsUnselectAll
    case manageNotificationsNoNotificationScheduled
    case unitsTitle
    case unitsVolume
    case unitsWeight
    case unitsTemperature

    var key: String {
        rawValue
    }

    var table: String {
        "Settings"
    }
}

extension Text {
    init(_ resource: SettingsSR) {
        self.init(resource.text)
    }
}
