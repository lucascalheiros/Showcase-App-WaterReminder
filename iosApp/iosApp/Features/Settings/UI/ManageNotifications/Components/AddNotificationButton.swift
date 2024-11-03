//
//  AddNotificationButton.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AddNotificationButton: View {
    @EnvironmentObject var theme: ThemeManager
    var isSheetVisible: Bool
    var sendIntent: (ManageNotificationsIntent) -> Void

    var body: some View {
        Button {
            sendIntent(.showAddNotifications)
        } label: {
            ImageResources.notificationAdd.image()
                .padding()
                .background(theme.current.surfaceColor)
                .foregroundColor(theme.current.onSurfaceColor)
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .shadow(radius: 2)

        }
        .sheet(isPresented: isSheetVisible.bindingWithDismiss {
            sendIntent(.dismissAddNotifications)
        }) {
            AddNotificationsSheet(onCancel: {
                sendIntent(.dismissAddNotifications)
            }, onConfirm: {
                sendIntent(.confirmAddNotifications($0))
            })
            .presentationDetents([.medium])
        }
        .padding()
        .transaction { transaction in
            transaction.disablesAnimations = false
        }
    }
}

