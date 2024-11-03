//
//  NotificationInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import DesignSystem

struct NotificationInputPage: View {
    @EnvironmentObject var theme: ThemeManager
    @State var startTime = Calendar.current.date(bySettingHour: 8, minute: 0, second: 0, of: Date())!
    @State var endTime = Calendar.current.date(bySettingHour: 20, minute: 0, second: 0, of: Date())!
    @State var period = Calendar.current.date(bySettingHour: 2, minute: 0, second: 0, of: Date())!
    @State var notificationDisabled = false

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Text("Let us help you to remind drinking water. This can be changed lates on settings.")
                SettingGroupContainer {
                    SettingItemContainer {
                        DatePicker("Start at", selection: $startTime, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        DatePicker("End at", selection: $endTime, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        DatePicker("Notify me each", selection: $period, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("I don't want notifications", isOn: $notificationDisabled)
                            .tint(theme.current.primaryColor)
                    }
                }
            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }
}
