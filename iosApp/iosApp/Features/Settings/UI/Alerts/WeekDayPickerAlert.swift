//
//  WeekDayPickerAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem

extension View {
    func showWeekDayPickerAlert(
        showAlert: Bool,
        onDismiss: @escaping () -> Void,
        onConfirm: @escaping (WeekDayState) -> Void
    ) -> some View {
        fullScreenCover(isPresented: showAlert.bindingWithDismiss {
            onDismiss()
        }) {
            WeekDayPickerAlert(onDismiss: {
                onDismiss()
            }, onConfirm: {
                onConfirm($0)
            })
        }
        .transaction { transaction in
            transaction.disablesAnimations = true
        }
    }
}

private struct WeekDayPickerAlert: View {
    @EnvironmentObject var theme: ThemeManager
    @State var weekState: WeekDayState? = WeekDayState.everyDay()
    var onDismiss: () -> Void
    var onConfirm: (WeekDayState) -> Void

    var body: some View {
        CustomAlertView(
            title: SettingsSR.weekDaysAlertTitleForSelection.text,
            content: {
                WeekDayPicker(weekDayState: $weekState)
                    .frame(maxHeight: 100)
            },
            buttons: {
                HStack(spacing: 0) {
                    Button(action: {
                        onDismiss()
                    }) {
                        Text(SettingsSR.weekDaysAlertCancel.text)
                    }
                    .buttonStyle(AlertButtonStyle())
                    Divider()
                    Button(action: {
                        guard let weekState else {
                            onDismiss()
                            return
                        }
                        onConfirm(weekState)
                    }) {
                        Text(SettingsSR.weekDaysAlertConfirm.text)
                    }
                    .buttonStyle(AlertButtonStyle())
                }
            }
        )
        .presentationBackground(.clear)
    }
}
