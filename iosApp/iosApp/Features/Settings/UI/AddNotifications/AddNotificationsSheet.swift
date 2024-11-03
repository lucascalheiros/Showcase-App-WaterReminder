//
//  AddNotificationsSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AddNotificationsSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @State private var singleOrMultiple: AddNotificationType = .single
    @State private var multipleData: MultipleNotifications = MultipleNotifications()
    @State private var singleData: SingleNotification = SingleNotification()
    @State private var popoverData: AddNotificationsWeekDayPopoverData?
    @Namespace private var weekDayPopover
    var onCancel: () -> Void
    var onConfirm: (AddNotificationResult) -> Void

    private func showPopover(target: AddNotificationsWeekDayPopoverData) {
        if popoverData != nil {
            withAnimation {
                popoverData = nil
            } completion: {
                popoverData = target
            }
        } else {
            popoverData = target
        }
    }

    private var result: AddNotificationResult {
        switch singleOrMultiple {
        case .single:
                .single(singleData)
        case .multiple:
                .multiple(multipleData)
        }
    }

    var body: some View {
        ZStack {
            VStack {
                header
                Picker(selection: $singleOrMultiple, label: Text("")) {
                    Text(SettingsSR.addNotificationsSingle.text).tag(AddNotificationType.single)
                    Text(SettingsSR.addNotificationsMultiple.text).tag(AddNotificationType.multiple)
                }
                .pickerStyle(.segmented)
                SettingGroupContainer {
                    switch singleOrMultiple {
                    case .single:
                        SingleNotificationSection(weekDayPopover: weekDayPopover, singleData: $singleData, popover: $popoverData)
                    case .multiple:
                        MultipleNotificationsSection(weekDayPopover: weekDayPopover, multipleData: $multipleData, popover: $popoverData)
                    }
                }
                Spacer()
            }
            .padding(16)
            .background(theme.current.backgroundColor)
            .onAppear(perform: {
            })
            popovers
                .transition(
                    .opacity.combined(with: .scale)
                    .animation(.bouncy(duration: 0.25, extraBounce: 0.2))
                )
        }
    }

    @ViewBuilder
    private var popovers: some View {
        if let popoverData {

            Rectangle()
                .foregroundColor(.clear)
                .contentShape(Rectangle())
                .onTapGesture {
                    switch popoverData.type {

                    case .single:
                        singleData.notificationDays = popoverData.weekDayState

                    case .multiple:
                        multipleData.notificationDays = popoverData.weekDayState
                    }
                    withAnimation {
                        self.popoverData = nil
                    }
                }

            WeekDayPicker(weekDayState: Binding(
                get: { popoverData.weekDayState },
                set: { newValue in
                    if let newValue {
                        self.popoverData?.weekDayState = newValue
                    }
                }
            ))
            .padding()
            .background {
                RoundedRectangle(cornerRadius: 10)
                    .fill(theme.current.surfaceColor)
                    .shadow(radius: 6)
            }
            .matchedGeometryEffect(
                id: popoverData.type,
                in: weekDayPopover,
                properties: .position,
                anchor: .trailing,
                isSource: false
            )
        }
    }

    var header: some View {
        HStack {
            Button(SettingsSR.addNotificationsCancel.text) {
                onCancel()
            }
            Spacer()
            Text(SettingsSR.addNotificationsTitle.text)
                .font(theme.current.titleSmall)
            Spacer()
            Button(SettingsSR.addNotificationsConfirm.text) {
                onConfirm(result)
            }
        }
        .tint(theme.current.onBackgroundColor)
    }
}

struct AddNotificationsWeekDayPopoverData {
    let type: AddNotificationType
    var weekDayState: WeekDayState
}

enum AddNotificationType {
    case single
    case multiple
}
