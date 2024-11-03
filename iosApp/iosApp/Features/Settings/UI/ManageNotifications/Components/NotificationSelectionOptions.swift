//
//  NotificationSelectionOptions.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import DesignSystem

struct NotificationSelectionOptions: View {
    @EnvironmentObject var theme: ThemeManager
    @State var _allSelected: Bool = false
    var allSelected: Bool
    var sendIntent: (ManageNotificationsIntent) -> Void

    var body: some View {
        HStack(alignment: .bottom, spacing: 0) {
            button(
                SettingsSR.manageNotificationOptionsDays.text,
                ImageResources.calendar.image()
            ) {
                sendIntent(.showWeekDayPickerForSelected)
            }
            button(
                SettingsSR.manageNotificationOptionsDelete.text,
                ImageResources.deleteIcon.image()
            ) {
                sendIntent(.deleteSelected)
            }
            button(
                (_allSelected ? SettingsSR.manageNotificationOptionsUncheckAll : SettingsSR.manageNotificationOptionsCheckAll).text,
                (_allSelected ? ImageResources.square : ImageResources.checkMarkSquare).image()
            ) {
                sendIntent(.toggleAllSelection)
            }
            button(
                SettingsSR.manageNotificationOptionsCancel.text,
                ImageResources.cancelCircle.image()
            ) {
                sendIntent(.disableSelectionMode)
            }
        }
        .frame(height: 58)
        .background(theme.current.surfaceColor)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .shadow(radius: 2)
        .font(theme.current.caption)
        .padding()
        .onChange(of: allSelected) { old, new in
            withAnimation {
                _allSelected = new
            }
        }
    }

    func button(_ text: String, _ image: Image, action: @escaping () -> Void) -> some View {
        Button {
            action()
        } label: {
            VStack(spacing: 0) {
                image
                    .resizable()
                    .scaledToFit()
                Text(text)
            }
        }.padding(12)
    }
}
