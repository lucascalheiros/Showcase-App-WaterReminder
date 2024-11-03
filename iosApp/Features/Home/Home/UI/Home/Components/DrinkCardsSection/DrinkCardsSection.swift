//
//  DrinkCardsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem

struct DrinkCardsSection: View {
    var cards: [DrinkCard]
    var addDrinkSheetVisible: Bool
    var drinkShortcutSheetVisible: WaterSourceType?
    var sendIntent: (HomeIntent) -> Void

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 8) {
                ForEach(cards) { card in
                    switch card {
                    case .item(let drink):
                        DrinkCardView(
                            drink: drink,
                            onDelete: {
                                sendIntent(.onDeleteDrinkClick(drink))
                            },
                            onClick: {
                                sendIntent(.onDrinkClick(drink))
                            })
                    case .addItem:
                        AddDrinkCardView {
                            sendIntent(.onAddDrinkClick)
                        }
                    }
                }
            }
            .frame(
                maxHeight: 40,
                alignment: .center
            )
            .padding(.horizontal, 16)
        }
        .sheet(isPresented: addDrinkSheetVisible.bindingWithDismiss {
            sendIntent(.onAddDrinkDismissed)
        }) {
            AddDrinkBottomSheet(onDismiss: { sendIntent(.onAddDrinkDismissed) })
                .presentationDetents([.medium])
                .onDisappear(perform: {
                    sendIntent(.onAddDrinkDismissed)
                })
        }
        .sheet(item: drinkShortcutSheetVisible.bindingWithDismiss {
            sendIntent(.onDrinkShortcutDismissed)
        }) {
            DrinkShortcutBottomSheet(
                selectedDrink: $0,
                onDismiss: { sendIntent(.onDrinkShortcutDismissed) }
            )
            .presentationDetents([.medium])
            .onDisappear(perform: {
                sendIntent(.onDrinkShortcutDismissed)
            })
        }
    }
}
