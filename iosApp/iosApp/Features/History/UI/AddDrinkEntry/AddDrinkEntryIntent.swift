//
//  AddDrinkEntryIntent.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

enum AddDrinkEntryIntent {
    case initData
    case onVolumeClick
    case onVolumeInputConfirm(Double)
    case onVolumeInputCancel
    case onDrinkClick
    case onDrinkSelected(WaterSourceType)
    case onDateSelected(Date)
    case onDrinkSelectCancel
    case onConfirmClick
    case onCancelClick
}
