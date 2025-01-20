//
//  AddDrinkEntryBottomSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AddDrinkEntryBottomSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var viewModel = AddDrinkEntryViewModel()
    @State var volumeInput: Double? = nil
    @State var selectedDrink: WaterSourceType? = nil
    @Environment(\.colorScheme) var colorScheme
    var onDismiss: () -> Void

    var valueColor: Color {
        viewModel.state.drink?.color(colorScheme) ?? theme.current.onSurfaceColor
    }

    var body: some View {
        if viewModel.state.isDismissed {
            onDismiss()
        }

        return VStack {
            header
            SettingGroupContainer {
                volumeSettingItem
                SettingItemDivider()
                drinkSettingItem
                SettingItemDivider()
                dateTimeSettingItem
            }
            Spacer()
        }
        .padding(16)
        .background(theme.current.backgroundColor)
        .onAppear(perform: {
            viewModel.send(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                viewModel.send(.onCancelClick)
            }, label: {
                Text(.alertCancel)
            })
            .tint(theme.current.onBackgroundColor)
            Spacer()
            Text(.addCupTitle)
                .font(theme.current.titleSmall)
            Spacer()
            Button(action: {
                viewModel.send(.onConfirmClick)
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
            Text(viewModel.state.volume?.shortValueAndUnitFormatted ?? "")
                .foregroundStyle(valueColor)
                .bold()
        })
        .volumeInputAlert(
            showAlert: $viewModel.state.showVolumeInputAlert,
            volumeInput: $volumeInput,
            onCancel: { viewModel.send(.onVolumeInputCancel) },
            onConfirm: { viewModel.send(.onVolumeInputConfirm($0)) }
        )
        .onTapGesture {
            volumeInput = viewModel.state.volume?.intrinsicValue() ?? 0.0
            viewModel.send(.onVolumeClick)
        }
    }

    var drinkSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addCupDrinkOption)
        }, value: {
            Text(viewModel.state.drink?.name ?? "")
                .foregroundStyle(valueColor)
                .bold()
        })
        .selectDrinkAlert(
            showAlert: $viewModel.state.showSelectDrinkAlert,
            availableDrinks: viewModel.state.availableDrinks,
            onCancel: { viewModel.send(.onDrinkSelectCancel) },
            onSelect: { viewModel.send(.onDrinkSelected($0)) }
        )
        .onTapGesture {
            viewModel.send(.onDrinkClick)
        }
    }

    var dateTimeSettingItem: some View {
        SettingItemContainer {
            DatePicker(HistorySR.addEntryDateTime.text, selection: viewModel.state.dateTime.bindingWith {
                viewModel.send(.onDateSelected($0))
            }, displayedComponents: [.date, .hourAndMinute])
        }


    }
}
