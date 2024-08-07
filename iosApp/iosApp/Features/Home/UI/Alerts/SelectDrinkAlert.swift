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
    func selectDrinkAlert(
        showAlert: Binding<Bool>,
        availableDrinks: [WaterSourceType],
        onCancel: @escaping () -> Void,
        onSelect: @escaping (WaterSourceType) -> Void
    ) -> some View {
        fullScreenCover(isPresented: showAlert) {
            CustomAlertView(
                title: String(localized: "Select a Drink"),
                content: {
                    DrinkListView(availableDrinks: availableDrinks) {
                        onSelect($0)
                    }
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

private struct DrinkListView: View {
    @Environment(\.colorScheme) var colorScheme
    let availableDrinks: [WaterSourceType]
    let onSelect: (WaterSourceType) -> Void

    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                ForEach(availableDrinks, id: \.waterSourceTypeId) { item in
                    Divider()
                    Button(action: {
                        onSelect(item)
                    }) {
                        Text(item.name)
                            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 48, maxHeight: 48)
                            .contentShape(Rectangle())
                    }
                    .tint(item.color(colorScheme))
                }
                Divider()
            }
        }
        .frame(maxHeight: 200)
    }
}
