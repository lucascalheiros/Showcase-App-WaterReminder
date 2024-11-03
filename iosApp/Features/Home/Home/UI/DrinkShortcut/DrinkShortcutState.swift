//
//  DrinkShortcutState.swift
//  Home
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared

struct DrinkShortcutState {
    var isDismissed = false
    var selectedWater: WaterSourceType
    var selectedVolumeIndex: Int = 0
    var defaultVolumeShortcuts: DefaultVolumeShortcuts? = nil
    var volumeOptions: [MeasureSystemVolume] = []
    var hydration: Float? = nil
    var color: ThemeAwareColor? = nil
    var showNameInputAlert: Bool = false
    var showSelectHydrationAlert: Bool = false
    var showSelectColorAlert: Bool = false
}
