//
//  DrinkShortcutIntent.swift
//  Home
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared

enum DrinkShortcutIntent {
    case initData
    case onCancelClick
    case onConfirmClick
    case onSelectedVolume(MeasureSystemVolume)
}
