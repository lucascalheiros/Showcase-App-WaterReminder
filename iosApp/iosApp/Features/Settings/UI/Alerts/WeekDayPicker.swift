//
//  WeekDayPickerAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem

struct WeekDayPicker: View {
    @EnvironmentObject var theme: ThemeManager
    @Binding var weekDayState: WeekDayState?

    var body: some View {
        HStack(spacing: 0) {
            Toggle(WeekDay.sunday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.sunday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.sunday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.monday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.monday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.monday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.tuesday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.tuesday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.tuesday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.wednesday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.wednesday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.wednesday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.thursday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.thursday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.thursday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.friday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.friday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.friday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())

            Toggle(WeekDay.saturday.formatShortNumbered, isOn: Binding(
                get: { weekDayState?.saturday ?? false },
                set: { newValue in
                    if weekDayState != nil {
                        weekDayState?.saturday = newValue
                    }
                }
            ))
            .toggleStyle(CircleButtonToggleStyle())
        }
        .fixedSize()
        .font(theme.current.bodyLarge)
        .foregroundStyle(theme.current.primaryColor)
    }
}

struct WeekDayState: Identifiable, Equatable, Hashable {
    let id: UUID = UUID()

    var sunday: Bool
    var monday: Bool
    var tuesday: Bool
    var wednesday: Bool
    var thursday: Bool
    var friday: Bool
    var saturday: Bool

    static func everyDay() -> WeekDayState {
        WeekDayState(sunday: true, monday: true, tuesday: true, wednesday: true, thursday: true, friday: true, saturday: true)
    }
}
