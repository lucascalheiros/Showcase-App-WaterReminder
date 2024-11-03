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

    var state: DrinkShortcutState {
        drinkShortcutViewModel.state
    }

    var sendIntent: (DrinkShortcutIntent) -> Void {
        drinkShortcutViewModel.send
    }

    init(selectedDrink: WaterSourceType, onDismiss: @escaping () -> Void) {
        _drinkShortcutViewModel = StateObject(wrappedValue: DrinkShortcutViewModel(selectedDrink))
        self.onDismiss = onDismiss
    }

    var body: some View {
        VStack {
            header
                .padding(16)
            volumeShortcutCards
                .frame(
                    maxWidth: .infinity,
                    maxHeight: 50
                )
                .padding(.horizontal, 16)
                .zIndex(1.0)
            volumePicker
                .scaleEffect(1.4)
                .frame(
                    minHeight: 0,
                    maxHeight: .infinity
                )
        }
        .onChange(of: state.isDismissed) { old, new in
            if new {
                onDismiss()
            }
        }
        .background(theme.current.backgroundColor)
        .onAppear(perform: {
            sendIntent(.initData)
        })
    }

    var header: some View {
        HStack {
            Button(action: {
                sendIntent(.onCancelClick)
            }, label: {
                Text(.alertCancel)
            })
            .tint(theme.current.onBackgroundColor)
            Spacer()
            Text(state.selectedWater.name)
                .font(theme.current.titleSmall)
            Spacer()
            Button(action: {
                sendIntent(.onConfirmClick)
            }, label: {
                Text(.drinkShortcutConfirm)
            })
            .tint(theme.current.onBackgroundColor)
        }
    }

    var volumeShortcutCards: some View {
        HStack {
            if let volumeShortcut =  state.defaultVolumeShortcuts {
                VolumeShortcutCard(volume: volumeShortcut.smallest)
                    .onTapGesture {
                        sendIntent(.onSelectedVolume(volumeShortcut.smallest))
                    }
                VolumeShortcutCard(volume: volumeShortcut.small)
                    .onTapGesture {
                        sendIntent(.onSelectedVolume(volumeShortcut.small))
                    }
                VolumeShortcutCard(volume: volumeShortcut.medium)
                    .onTapGesture {                            sendIntent(.onSelectedVolume(volumeShortcut.medium))
                    }
                VolumeShortcutCard(volume: volumeShortcut.large)
                    .onTapGesture {
                        sendIntent(.onSelectedVolume(volumeShortcut.large))
                    }
            }
        }
    }

    var volumePicker: some View {
        Picker(
            "",
            selection: state.selectedVolumeIndex.bindingWith {
                sendIntent(.onSelectedVolume(state.volumeOptions[$0]))
            },
            content: {
                ForEach(Array(state.volumeOptions.enumerated()), id: \.offset) {
                    Text($0.element.shortValueAndUnitFormatted)
                        .font(theme.current.titleSmall)
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
