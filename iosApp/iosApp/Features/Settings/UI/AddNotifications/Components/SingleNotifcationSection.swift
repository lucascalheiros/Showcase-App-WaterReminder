//
//  SingleNotifcationSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SingleNotificationSection: View {
    var weekDayPopover: Namespace.ID
    @EnvironmentObject var theme: ThemeManager
    @Binding var singleData: SingleNotification
    @Binding var popover: AddNotificationsWeekDayPopoverData?

    var body: some View {
        VStack(spacing: 0) {
            SettingItemContainer {
                DatePicker(
                    SettingsSR.addNotificationsSingleNotificationTime.text,
                    selection: $singleData.notificationTime,
                    displayedComponents: .hourAndMinute
                )
            }
            SettingItemDivider()
            SettingItemContainer(
                SettingsSR.addNotificationsSingleNotificationDays.text
            ) {
                Button(singleData.notificationDays.formatWeekDaysDisplayText) {
                    $popover.wrappedValue = AddNotificationsWeekDayPopoverData(
                        type: .single,
                        weekDayState: singleData.notificationDays
                    )
                }
                .foregroundStyle(theme.current.primaryColor)
                .matchedGeometryEffect(id: AddNotificationType.single, in: weekDayPopover, anchor: .trailing)
            }
        }
    }
}
