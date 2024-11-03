//
//  HomeIntent.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

enum HomeIntent {
    case onAddCupClick
    case onAddCupDismissed
    case onCupClick(WaterSource)
    case onDeleteCupClick(WaterSource)
    case onMoveCupToPosition(WaterSource, position: Int)
    case onDrinkClick(WaterSourceType)
    case onDrinkShortcutDismissed
    case onMoveDrinkToPosition(WaterSourceType, position: Int)
    case onAddDrinkClick
    case onAddDrinkDismissed
    case onDeleteDrinkClick(WaterSourceType)
}
