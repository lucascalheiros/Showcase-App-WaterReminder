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
    @StateObject var addDrinkViewModel = AddDrinkViewModel()
    var onDismiss: () -> Void

    var valueColor: Color {
        switch colorScheme {

        case .dark:
            addDrinkViewModel.state.color.darkColor
        default:
            addDrinkViewModel.state.color.lightColor

        }
    }

    var state: AddDrinkState {
        addDrinkViewModel.state
    }

    var sendIntent: (AddDrinkIntent) -> Void {
        addDrinkViewModel.send
    }

    var body: some View {
        VStack {
            header
            SettingGroupContainer {
                nameSettingItem
                SettingItemDivider()
                hydrationSettingItem
                SettingItemDivider()
                colorSettingItem
            }
            Spacer()
        }
        .onChange(of: state.isDismissed) { old, new in
            if new {
                onDismiss()
            }
        }
        .padding(16)
        .background(theme.current.backgroundColor)
        .onAppear(perform: {
            addDrinkViewModel.send(.initData)
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
            Text(.addDrinkTitle)
                .font(theme.current.titleSmall)
            Spacer()
            Button(action: {
                sendIntent(.onConfirmClick)
            }, label: {
                Text(.alertConfirm)
            })
            .disabled(state.isConfirmDisabled)
            .foregroundColor(theme.current.onBackgroundColor.opacity(state.isConfirmDisabled ? 0.2 : 1.0))
        }
    }

    var nameSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addDrinkNameOption)
        }, value: {
            Text(addDrinkViewModel.state.name)
                .foregroundStyle(valueColor)
        })
        .nameInputAlert(
            showAlert: state.showNameInputAlert.bindingWithDismiss {
                sendIntent(.onNameAlertDismiss)
            },
            nameInput: state.name.bindingWith {
                sendIntent(.onNameChange($0))
            },
            onClose: { sendIntent(.onNameAlertDismiss) }
        )
        .onTapGesture {
            sendIntent(.onNameClick)
        }
    }

    var hydrationSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addDrinkHydrationOption)
        }, value: {
            Text(state.hydration.formatted())
                .foregroundStyle(valueColor)
        })
        .selectHydrationAlert(
            showAlert: state.showSelectHydrationAlert.bindingWithDismiss {
                sendIntent(.onHydrationAlertDismiss)
            },
            hydration: state.hydration.bindingWith {
                sendIntent(.onHydrationChange($0))
            },
            onClose: { sendIntent(.onHydrationAlertDismiss) }
        )
        .onTapGesture {
            sendIntent(.onHydrationClick)
        }
    }

    var colorSettingItem: some View {
        SettingItemContainer(title: {
            Text(.addDrinkColorOption)
        }, value: {
            ThemedColorView(themedColor: state.color)
                .frame(width: 40, height: 40)
        })
        .selectColorAlert(
            showAlert: state.showSelectColorAlert.bindingWithDismiss {
                sendIntent(.onColorAlertDismiss)
            },
            defaultColor: state.color,
            onCancel: { sendIntent(.onColorAlertDismiss) },
            onSelect: { sendIntent(.onColorChange($0)) }
        )
        .onTapGesture {
            sendIntent(.onColorClick)
        }
    }
}
