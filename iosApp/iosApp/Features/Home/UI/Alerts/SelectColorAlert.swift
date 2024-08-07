//
//  SelectDrinkAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension View {
    func selectColorAlert(
        showAlert: Binding<Bool>,
        defaultColor: ThemeAwareColor,
        onCancel: @escaping () -> Void,
        onSelect: @escaping (ThemeAwareColor) -> Void
    ) -> some View {
        fullScreenCover(isPresented: showAlert) {
            CustomAlertView(
                title: String(localized: "Select a Drink"),
                content: {
                    @Environment(\.colorScheme) var colorScheme
                    ScrollView {

                    }
                    .frame(maxHeight: 200)
                },
                buttons: {
                    Button(action: onCancel) {
                        Text("Cancel")
                    }
                    .buttonStyle(AlertButtonStyle())
                }
            )
            .presentationBackground(.clear)
        }
        .transaction { transaction in
            transaction.disablesAnimations = true
            transaction.animation = .linear(duration: 0.1)
        }
    }
}
