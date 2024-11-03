//
//  AddCupBottomSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem
import KMPShared

struct AddCupBottomSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var addCupViewModel = AddCupViewModel()
    @State var volumeInput: Double? = nil
    @State var selectedDrink: WaterSourceType? = nil
    @Environment(\.colorScheme) var colorScheme
    var onDismiss: () -> Void

    var valueColor: Color {
        addCupViewModel.state.drink?.color(colorScheme) ?? theme.current.onSurfaceColor
    }

    var body: some View {
        if addCupViewModel.state.isDismissed {
            onDismiss()
        }

        return VStack {
            header
            SettingGroupContainer {
                volumeSettingItem
                SettingItemDivider()
                drinkSettingItem
            }
            Spacer()
        }
        .padding(16)
        .background(theme.current.backgroundColor)
        .onAppear(perform: {
            addCupViewModel.send(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                addCupViewModel.send(.onCancelClick)
            }, label: {
                Text(.alertCancel)
            })
            .tint(theme.current.onBackgroundColor)
            Spacer()
            Text(.addCupTitle)
                .font(theme.current.titleSmall)
            Spacer()
            Button(action: {
                addCupViewModel.send(.onConfirmClick)
            }, label: {
                Text(.alertConfirm)
            })
            .tint(theme.current.onBackgroundColor)
        }
    }

    var volumeSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addCupVolumeOption)
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
            volumeInput = addCupViewModel.state.volume?.intrinsicValue() ?? 0.0
            addCupViewModel.send(.onVolumeClick)
        }
    }

    var drinkSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addCupDrinkOption)
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
