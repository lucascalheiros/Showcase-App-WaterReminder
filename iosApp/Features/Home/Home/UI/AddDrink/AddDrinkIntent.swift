//
//  AddDrinkIntent.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import DesignSystem

enum AddDrinkIntent {
    case initData
    case onCancelClick
    case onConfirmClick
    case onNameClick
    case onNameAlertDismiss
    case onNameChange(String)
    case onHydrationClick
    case onHydrationAlertDismiss
    case onHydrationChange(Float)
    case onColorClick
    case onColorAlertDismiss
    case onColorChange(ThemedColor)
}
