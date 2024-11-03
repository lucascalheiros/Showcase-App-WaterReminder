//
//  AddCupBottomSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AddCupBottomSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var addCupViewModel = AddCupViewModel.create()
    @State var volumeInput: Double? = nil
    @State var selectedDrink: WaterSourceType? = nil
    @Environment(\.colorScheme) var colorScheme
    var onDismiss: () -> Void

    var valueColor: Color {
        addCupViewModel.state.drink?.color(colorScheme) ?? theme.selectedTheme.onSurfaceColor
    }

    var body: some View {
        if addCupViewModel.state.isDismissed {
            onDismiss()
        }

        return VStack {
            header.padding(16)
            SettingGroupContainer {
                volumeSettingItem
                SettingItemDivider()
                drinkSettingItem
            }
            Spacer()
        }
        .background(theme.selectedTheme.backgroundColor)
        .onAppear(perform: {
            addCupViewModel.send(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                addCupViewModel.send(.onCancelClick)
            }, label: {
                Text("Cancel")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
            Spacer()
            StyledText("Add Cup")
            Spacer()
            Button(action: {
                addCupViewModel.send(.onConfirmClick)
            }, label: {
                Text("Confirm")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
        }
    }

    var volumeSettingItem: some View {
        SettingItemContainer(title: {
            Text("Volume")
        }, value: {
            Text(addCupViewModel.state.volume?.shortValueAndUnitFormatted ?? "")
                .foregroundStyle(valueColor)
                .bold()
        })
        .volumeInputAlert(
            showAlert: $addCupViewModel.state.showVolumeInputAlert,
            volumeInput: $volumeInput,
            onCancel: { addCupViewModel.send(.onVolumeInputCancel) },
            onConfirm: { addCupViewModel.send(.onVolumeInputConfirm($0)) }
        )
        .onTapGesture {
            volumeInput = nil
            addCupViewModel.send(.onVolumeClick)
        }
    }

    var drinkSettingItem: some View {
        SettingItemContainer(title: {
            Text("Drink")
        }, value: {
            Text(addCupViewModel.state.drink?.name ?? "")
                .foregroundStyle(valueColor)
                .bold()
        })
        .selectDrinkAlert(
            showAlert: $addCupViewModel.state.showSelectDrinkAlert,
            availableDrinks: addCupViewModel.state.availableDrinks,
            onCancel: { addCupViewModel.send(.onDrinkSelectCancel) },
            onSelect: { addCupViewModel.send(.onDrinkSelected($0)) }
        )
        .onTapGesture {
            addCupViewModel.send(.onDrinkClick)
        }
    }
}
