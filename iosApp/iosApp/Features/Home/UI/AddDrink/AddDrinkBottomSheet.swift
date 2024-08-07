//
//  AddDrinkBottomSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AddDrinkBottomSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    @StateObject var addDrinkViewModel = AddDrinkViewModel.create()
    @State var nameInput: String = ""
    @State var hydrationFactor: Float = 1
    @State var selectedDrink: WaterSourceType? = nil
    var onDismiss: () -> Void

    var valueColor: Color {
        addDrinkViewModel.state.color?.color(colorScheme) ?? theme.selectedTheme.onSurfaceColor
    }

    var body: some View {
        if addDrinkViewModel.state.isDismissed {
            onDismiss()
        }

        return VStack {
            header.padding(16)
            SettingGroupContainer {
                nameSettingItem
                SettingItemDivider()
                hydrationSettingItem
                SettingItemDivider()
                colorSettingItem
            }
            Spacer()
        }
        .background(theme.selectedTheme.backgroundColor)
        .onAppear(perform: {
            addDrinkViewModel.send(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                addDrinkViewModel.send(.onCancelClick)
            }, label: {
                Text("Cancel")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
            Spacer()
            StyledText("Add Drink")
            Spacer()
            Button(action: {
                addDrinkViewModel.send(.onConfirmClick)
            }, label: {
                Text("Confirm")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
        }
    }

    var nameSettingItem: some View {
        SettingItemContainer(title: {
            Text("Name")
        }, value: {
            Text(addDrinkViewModel.state.name)
                .foregroundStyle(valueColor)
        })
        .nameInputAlert(
            showAlert: $addDrinkViewModel.state.showNameInputAlert,
            nameInput: $nameInput,
            onCancel: { addDrinkViewModel.send(.onNameAlertDismiss) },
            onConfirm: { addDrinkViewModel.send(.onNameChange($0)) }
        )
        .onTapGesture {
            addDrinkViewModel.send(.onNameClick)
        }
    }

    var hydrationSettingItem: some View {
        SettingItemContainer(title: {
            Text("Hydration Factor")
        }, value: {
            Text("TODO")
                .foregroundStyle(valueColor)
        })
        .selectHydrationAlert(
            showAlert: $addDrinkViewModel.state.showSelectHydrationAlert,
            hydration: $hydrationFactor,
            onCancel: { addDrinkViewModel.send(.onHydrationAlertDismiss) },
            onConfirm: { addDrinkViewModel.send(.onHydrationChange($0)) }
        )
        .onTapGesture {
            addDrinkViewModel.send(.onHydrationClick)
        }
    }

    var colorSettingItem: some View {
        SettingItemContainer(title: {
            Text("Color")
        }, value: {
            Text("TODO")
                .foregroundStyle(valueColor)
        })
        .selectColorAlert(
            showAlert: $addDrinkViewModel.state.showSelectColorAlert,
            defaultColor: ThemeAwareColor(onLightColor: 0, onDarkColor: 0),
            onCancel: { addDrinkViewModel.send(.onColorAlertDismiss) },
            onSelect: { addDrinkViewModel.send(.onColorChange($0)) }
        )
        .onTapGesture {
            addDrinkViewModel.send(.onColorClick)
        }
    }}
