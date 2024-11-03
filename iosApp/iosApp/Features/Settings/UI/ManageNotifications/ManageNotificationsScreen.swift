//
//  ManageNotificationsScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct ManageNotificationsScreen: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var manageNotificationsViewModel = ManageNotificationsViewModel()
    @Namespace private var weekDayPopover
    @State var isInSelectionMode: Bool = false
    @State var allSelected: Bool = false

    var state: ManageNotificationsState {
        manageNotificationsViewModel.state
    }

    var sendIntent: (ManageNotificationsIntent) -> Void {
        manageNotificationsViewModel.send
    }

    var body: some View {
        ZStack {
            ScreenRootLayout {
                SettingItemDivider()
                ZStack {
                    NotificationInfoList(
                        weekDayPopover: weekDayPopover,
                        notificationInfoList: state.notificationInfoList,
                        selectionMap: state.selectionMap,
                        selectionMode: state.isInSelectionMode,
                        sendIntent: sendIntent)
                    VStack {
                        Spacer()
                        if !isInSelectionMode {
                            HStack {
                                Spacer()
                                AddNotificationButton(
                                    isSheetVisible: state.showAddNotificationsSheet,
                                    sendIntent: sendIntent)
                            }
                        } else {
                            NotificationSelectionOptions(allSelected: allSelected, sendIntent: sendIntent)
                        }
                    }
                }
            }
            .navigationTitle(SettingsSR.manageNotificationsTitle.text)
            .navigationBarTitleDisplayMode(.inline)
            .manageNotificationsToolbar(isAllSelected: allSelected, onToggleSelection: {
                sendIntent(.toggleAllSelection)
            })
            .showWeekDayPickerAlert(
                showAlert: state.showWeekDayPickerForSelection,
                onDismiss: {
                    sendIntent(.dismissWeekDayPicker)
                },
                onConfirm: {
                    sendIntent(.setWeekStateForSelection($0.weekState))
                }
            )
            WeekDayPickerPopover(
                weekDayPopover: weekDayPopover,
                popoverData: state.popoverData,
                sendIntent: sendIntent
            )
        }
        .onChange(of: state.isInSelectionMode) { old, new in
            withAnimation {
                isInSelectionMode = new
            }
        }
        .onChange(of: state.allSelected) { old, new in
            allSelected = new
        }
    }
}

struct ManageNotificationsScreen_Preview: PreviewProvider {
    static let themeManager = ThemeManager()

    static var previews: some View {
        ManageNotificationsScreen(isInSelectionMode: true)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .environmentObject(themeManager)
    }
}

