//
//  MultipleNotificationsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MultipleNotificationsSection: View {
    var weekDayPopover: Namespace.ID
    @EnvironmentObject var theme: ThemeManager
    @Binding var multipleData: MultipleNotifications
    @Binding var popover: AddNotificationsWeekDayPopoverData?

    var body: some View {
        VStack(spacing: 0) {
            SettingItemContainer {
                DatePicker(
                    SettingsSR.addNotificationsMultipleStartAt.text,
                    selection: $multipleData.startTime,
                    displayedComponents: .hourAndMinute
                )
            }
            SettingItemDivider()
            SettingItemContainer {
                DatePicker(
                    SettingsSR.addNotificationsMultipleStoptAt.text,
                    selection: $multipleData.stopTime,
                    displayedComponents: .hourAndMinute
                )
            }
            SettingItemDivider()
            SettingItemContainer {
                DatePicker(
                    SettingsSR.addNotificationsMultipleNotifyEach.text,
                    selection: $multipleData.notifyEach,
                    displayedComponents: .hourAndMinute
                )
            }
            SettingItemDivider()
            SettingItemContainer(
                SettingsSR.addNotificationsMultipleNotificationDays.text
            ) {
                Button(multipleData.notificationDays.formatWeekDaysDisplayText) {
                    $popover.wrappedValue = AddNotificationsWeekDayPopoverData(
                        type: .multiple,
                        weekDayState: multipleData.notificationDays
                    )
                }
                .foregroundStyle(theme.current.primaryColor)
                .matchedGeometryEffect(id: AddNotificationType.multiple, in: weekDayPopover, anchor: .trailing)
            }
            SettingItemDivider()
            SettingItemContainer {
                Toggle(SettingsSR.addNotificationsMultipleDeleteOthers.text, isOn: $multipleData.deleteOther)
            }
        }
        .tint(theme.current.primaryColor)
    }
}
