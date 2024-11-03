//
//  NotificationInfoList.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct NotificationInfoList: View {
    @EnvironmentObject var theme: ThemeManager
    var weekDayPopover: Namespace.ID
    var notificationInfoList: [NotificationInfo]
    var selectionMap: [Int32:Bool]
    var selectionMode: Bool
    var sendIntent: (ManageNotificationsIntent) -> Void
    @State private var _notificationInfoList: [NotificationInfo] = []

    @ViewBuilder
    var body: some View {
        if _notificationInfoList.isEmpty {
            VStack(alignment: .center) {
                Text(SettingsSR.manageNotificationsNoNotificationScheduled.text)
            }
        }
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(Array(_notificationInfoList.enumerated()), id: \.element) { (index, notificationInfo) in
                    NotificationItem(
                        weekDayPopover: weekDayPopover,
                        info: notificationInfo,
                        selectionMode: selectionMode,
                        selected: selectionMap[notificationInfo.id] ?? false,
                        sendIntent: sendIntent)
                    if index != notificationInfoList.indices.last {
                        SettingItemDivider()
                    }
                }
            }
            .onChange(of: notificationInfoList) { old, new in
                guard !old.isEmpty else {
                    _notificationInfoList = new
                    return
                }
                withAnimation {
                    _notificationInfoList = new
                }
            }
            .onAppear {
                _notificationInfoList = notificationInfoList
            }
            .background(theme.current.surfaceColor)
            .cornerRadius(12)
            .padding(16)
        }
    }
}

struct NotificationInfoList_Preview: PreviewProvider {
    static func weekState() -> WeekState {
        WeekState(sundayEnabled: true, mondayEnabled: true, tuesdayEnabled: true, wednesdayEnabled: true, thursdayEnabled: true, fridayEnabled: true, saturdayEnabled: true)
    }
    static let data = {
        [
            NotificationInfo(dayTime: DayTime(hour: 8, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 10, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 12, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 15, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 17, minute: 30), weekState: weekState())
        ]
    }()
    static var previews: some View {
        NotificationInfoList(
            weekDayPopover: Namespace().wrappedValue,
            notificationInfoList: data,
            selectionMap: [:],
            selectionMode: false,
            sendIntent: { _ in })
        .environmentObject(ThemeManager())
    }
}

struct NotificationInfoList1_Preview: PreviewProvider {
    static func weekState() -> WeekState {
        WeekState(sundayEnabled: true, mondayEnabled: true, tuesdayEnabled: true, wednesdayEnabled: true, thursdayEnabled: true, fridayEnabled: true, saturdayEnabled: true)
    }
    static let data = {
        [
            NotificationInfo(dayTime: DayTime(hour: 8, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 10, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 12, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 15, minute: 30), weekState: weekState()),
            NotificationInfo(dayTime: DayTime(hour: 17, minute: 30), weekState: weekState())
        ]
    }()
    static var previews: some View {
        NotificationInfoList(
            weekDayPopover: Namespace().wrappedValue,
            notificationInfoList: data,
            selectionMap: [:], selectionMode: true,
            sendIntent: { _ in })
        .environmentObject(ThemeManager())
    }
}

