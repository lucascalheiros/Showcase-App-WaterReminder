//
//  NotificationItem.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem

struct NotificationItem: View {
    @EnvironmentObject var theme: ThemeManager
    var weekDayPopover: Namespace.ID
    var info: NotificationInfo
    var selectionMode: Bool
    var selected: Bool
    var sendIntent: (ManageNotificationsIntent) -> Void
    @State private var _selectionMode: Bool = false
    @State private var _selected: Bool = false

    var body: some View {
        SettingItemContainer {
            HStack(spacing: 0) {
                if _selectionMode {
                    Toggle("", isOn: Binding(get: {
                        _selected
                    }, set: { newValue in
                        sendIntent(newValue ? .selectItem(info) : .unselectItem(info))
                    }))
                    .toggleStyle(CheckCircleButtonToggleStyle())
                    .padding(.trailing, 8)
                    .foregroundStyle(theme.current.primaryColor)
                }
                Text(info.formattedTimeShort)
                Spacer()
                Button(info.formatWeekDaysDisplayText) {
                    sendIntent(.showWeekDayPicker(WeekDayPopoverData(type: .single(info.dayTime), weekDayState: info.weekState.weekDayState)))
                }
                .bold()
                .foregroundStyle(theme.current.primaryColor)
                .matchedGeometryEffect(id: info.dayTime.dayMinutes, in: weekDayPopover, anchor: .trailing)
                .transaction {
                    $0.animation = nil
                }
            }
        }
        .onChange(of: selectionMode) { oldState, newState in
            withAnimation {
                _selectionMode = newState
            }
        }
        .onChange(of: selected) { oldState, newState in
            withAnimation {
                _selected = newState
            }
        }
        .onTapGesture {
            if selectionMode {
                sendIntent(selected ? .unselectItem(info) : .selectItem(info))
            }
        }
        .onLongPressGesture {
            withAnimation {
                sendIntent(.enableSelectionMode)
                sendIntent(.selectItem(info))
            }
        }
        .onAppear {
            _selectionMode = selectionMode
            _selected = selected
        }
    }
}

struct NotificationItem_Preview: PreviewProvider {
    static var previews: some View {
        NotificationItem(
            weekDayPopover: Namespace().wrappedValue,
            info: NotificationInfo(dayTime: DayTime(hour: 10, minute: 30), weekState: WeekState(sundayEnabled: true, mondayEnabled: true, tuesdayEnabled: true, wednesdayEnabled: true, thursdayEnabled: true, fridayEnabled: true, saturdayEnabled: true)),
            selectionMode: false,
            selected: false, sendIntent: { _ in })
        .environmentObject(ThemeManager())
    }
}
