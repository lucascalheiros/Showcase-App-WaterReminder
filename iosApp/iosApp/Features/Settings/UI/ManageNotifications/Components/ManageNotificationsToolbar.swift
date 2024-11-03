//
//  ManageNotificationsToolbar.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

extension View {
    func manageNotificationsToolbar(isAllSelected: Bool, onToggleSelection: @escaping () -> Void) -> some View {
        toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Menu {
                    Button {
                        onToggleSelection()
                    } label: {
                        let str = isAllSelected ? SettingsSR.manageNotificationsUnselectAll : SettingsSR.manageNotificationsSelectAll
                        Text(str.text)
                    }
                } label: {
                    ImageResources.verticalMore.image()
                }
            }
        }
    }
}

