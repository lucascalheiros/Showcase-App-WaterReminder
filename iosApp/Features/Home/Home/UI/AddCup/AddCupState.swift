//
//  AddCupState.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

struct AddCupState {
    var isDismissed = false
    var volume: MeasureSystemVolume? = nil
    var drink: WaterSourceType? = nil
    var showVolumeInputAlert: Bool = false
    var showSelectDrinkAlert: Bool = false
    var availableDrinks: [WaterSourceType] = []
}
