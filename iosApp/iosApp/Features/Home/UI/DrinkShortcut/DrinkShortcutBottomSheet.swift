//
//  DrinkShortcutBottomSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct DrinkShortcutBottomSheet: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    @StateObject var drinkShortcutViewModel: DrinkShortcutViewModel
    var onDismiss: () -> Void

    init(selectedDrink: WaterSourceType, onDismiss: @escaping () -> Void) {
        _drinkShortcutViewModel = StateObject(wrappedValue: DrinkShortcutViewModel.create(selectedDrink))
        self.onDismiss = onDismiss
    }

    var body: some View {
        if drinkShortcutViewModel.state.isDismissed {
            onDismiss()
        }

        return VStack {
            header
                .padding(16)
            volumeShortcutCards
                .frame(
                    minWidth: 0,
                    maxWidth: .infinity,
                    minHeight: 0,
                    maxHeight: 50
                )
                .padding(.horizontal, 16)
            volumePicker
                .scaleEffect(1.5)
                .frame(
                    minHeight: 0,
                    maxHeight: .infinity
                )
        }
        .background(theme.selectedTheme.backgroundColor)
        .onAppear(perform: {
            drinkShortcutViewModel.send(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                drinkShortcutViewModel.send(.onCancelClick)
            }, label: {
                Text("Cancel")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
            Spacer()
            StyledText(drinkShortcutViewModel.state.selectedWater.name)
            Spacer()
            Button(action: {
                drinkShortcutViewModel.send(.onConfirmClick)
            }, label: {
                Text("Drink")
            })
            .tint(theme.selectedTheme.onBackgroundColor)
        }
    }

    var volumeShortcutCards: some View {
        HStack {
            if let volumeShortcut =  drinkShortcutViewModel.state.defaultVolumeShortcuts {
                VolumeShortcutCard(volume: volumeShortcut.smallest)
                    .onTapGesture {
                        drinkShortcutViewModel.send(.onSelectedVolume(volumeShortcut.smallest))
                    }
                VolumeShortcutCard(volume: volumeShortcut.small)
                    .onTapGesture {
                        drinkShortcutViewModel.send(.onSelectedVolume(volumeShortcut.small))
                    }
                VolumeShortcutCard(volume: volumeShortcut.medium)
                    .onTapGesture {                            drinkShortcutViewModel.send(.onSelectedVolume(volumeShortcut.medium))
                    }
                VolumeShortcutCard(volume: volumeShortcut.large)
                    .onTapGesture {
                        drinkShortcutViewModel.send(.onSelectedVolume(volumeShortcut.large))
                    }
            }
        }
    }

    var volumePicker: some View {
        Picker("",
               selection: $drinkShortcutViewModel.state.selectedVolumeIndex,
               content: {
            ForEach(Array(drinkShortcutViewModel.state.volumeOptions.enumerated()), id: \.offset) {
                Text($0.element.shortValueAndUnitFormatted)
                    .font(theme.selectedTheme.titleSmall)
            }
        })
        .pickerStyle(.wheel)
    }

}

private struct VolumeShortcutCard: View {
    let volume: MeasureSystemVolume?

    var body: some View {
        CardView {
            Text(volume?.shortValueAndUnitFormatted ?? "")
        }
        .overlay(RoundedRectangle(cornerRadius: 10).stroke(.gray, lineWidth: 1))
    }
}
