//
//  NotificationInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI

struct NotificationInputPage: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var viewModel: NotificationInputViewModel = NotificationInputViewModel()
    @State var startTime = Calendar.current.date(bySettingHour: 8, minute: 0, second: 0, of: Date())!
    @State var stopTime = Calendar.current.date(bySettingHour: 20, minute: 0, second: 0, of: Date())!
    @State var period = Calendar.current.date(bySettingHour: 2, minute: 0, second: 0, of: Date())!
    @State var notificationDisabled = false

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Text("Let us help you to remind drinking water. This can be changed later on settings.")
                SettingGroupContainer {
                    SettingItemContainer {
                        DatePicker("Start at", selection: startTime.bindingWith {
                            viewModel.send(.setStartTime($0))
                        }, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        DatePicker("End at", selection: stopTime.bindingWith {
                            viewModel.send(.setStopTime($0))
                        }, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        DatePicker("Notify me each", selection: period.bindingWith {
                            viewModel.send(.setFrequency($0))
                        }, displayedComponents: .hourAndMinute)
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("I don't want notifications", isOn: notificationDisabled.bindingWith {
                            viewModel.send(.setDisabled($0))
                        })
                        .tint(theme.current.primaryColor)
                    }
                }
                .onChange(of: viewModel.state) { _, new in
                    withAnimation {
                        startTime = new.startTime
                        stopTime = new.stopTime
                        period = new.frequency
                        notificationDisabled = new.isDisabled
                    }
                }
                .onAppear {
                    viewModel.send(.loadData)
                }
            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }
}

extension Date {
    func bindingWith(_ onUpdate: @escaping (Date) -> Void) -> Binding<Date> {
        Binding(get: { self }, set: { onUpdate($0) })
    }
}


extension Bool {
    func bindingWith(_ onUpdate: @escaping (Bool) -> Void) -> Binding<Bool> {
        Binding(get: { self }, set: { onUpdate($0) })
    }
}
