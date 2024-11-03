//
//  DrinkCardsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct DrinkCardsSection: View {
    var drinks: [DrinkCard]
    var addDrinkSheetVisible: Binding<Bool>
    var drinkShortcutSheetVisible: Binding<WaterSourceType?>
    var sendIntent: (HomeIntent) -> Void
    @Environment(\.colorScheme) var colorScheme
    @EnvironmentObject var theme: ThemeManager

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 8) {
                ForEach(drinks) { card in
                    CardView {
                        switch card {
                        case .item(let drink):
                            Text(card.name)
                                .foregroundStyle(drink.color(colorScheme))
                                .padding()

                        case .addItem:
                            Text(card.name)
                                .padding()
                        }
                    }
                    .overlay(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 1)
                    )
                    .contextMenu {
                        if case .item(let drink) = card {
                            Button {
                                sendIntent(.onDeleteDrinkClick(drink))
                            } label: {
                                Text("Delete")
                                ImageResources.deleteIcon.image()
                            }
                        }
                    }
                    .sheet(isPresented: addDrinkSheetVisible) {
                        AddDrinkBottomSheet(onDismiss: { sendIntent(.onAddDrinkDismissed) })
                            .presentationDetents([.medium])
                            .onDisappear(perform: {
                                sendIntent(.onAddDrinkDismissed)
                            })
                    }
                    .sheet(item: drinkShortcutSheetVisible) { 
                        DrinkShortcutBottomSheet(
                            selectedDrink: $0,
                            onDismiss: { sendIntent(.onDrinkShortcutDismissed) }
                        )
                        .presentationDetents([.medium])
                        .onDisappear(perform: {
                            sendIntent(.onDrinkShortcutDismissed)
                        })
                    }
                    .onTapGesture {
                        switch card {

                        case .item(let data):
                            sendIntent(.onDrinkClick(data))
                        case .addItem:
                            sendIntent(.onAddDrinkClick)
                        }
                    }
                }
            }.frame(
                minWidth: 0,
                maxWidth: .infinity,
                minHeight: 0,
                maxHeight: 50,
                alignment: .center
            )
            .padding(.horizontal, 16)
        }
    }
}

extension WaterSourceType: Identifiable {
    public var id: Int64 {
        waterSourceTypeId
    }
}
