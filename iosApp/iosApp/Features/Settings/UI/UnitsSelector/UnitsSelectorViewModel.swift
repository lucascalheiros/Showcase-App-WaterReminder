//
//  UnitsSelectorViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Factory
import Combine
import Shared

class UnitsSelectorViewModel: ObservableObject {

    @Injected(\.getVolumeUnitUseCase)
    private var getVolumeUnitUseCase

    @Injected(\.getWeightUnitUseCase)
    private var getWeightUnitUseCase

    @Injected(\.getTemperatureUnitUseCase)
    private var getTemperatureUnitUseCase

    @Injected(\.setVolumeUnitUseCase)
    private var setVolumeUnitUseCase

    @Injected(\.setWeightUnitUseCase)
    private var setWeightUnitUseCase

    @Injected(\.setTemperatureUnitUseCase)
    private var setTemperatureUnitUseCase

    @Published private(set) var state: UnitsSelectorState = .init()

    func send(_ intent: UnitsSelectorIntent) {
        Task {
            switch intent {
            case .loadData:
                await loadData()
            case .setVolumeUnit(let unit):
                await setVolumeUnit(unit)
            case .setWeightUnit(let unit):
                await setWeightUnit(unit)
            case .setTemperatureUnit(let unit):
                await setTemperatureUnit(unit)

            }
        }
    }

    private func loadData() async {
        state.volumeUnit = try? await getVolumeUnitUseCase.execute()
        state.weightUnit = try? await getWeightUnitUseCase.execute()
        state.temperatureUnit = try? await getTemperatureUnitUseCase.execute()
    }

    private func setVolumeUnit(_ unit: MeasureSystemVolumeUnit) async {
        state.volumeUnit = unit
        try? await setVolumeUnitUseCase.execute(unit)
    }

    private func setWeightUnit(_ unit: MeasureSystemWeightUnit) async {
        state.weightUnit = unit
        try? await setWeightUnitUseCase.execute(unit)
    }

    private func setTemperatureUnit(_ unit: MeasureSystemTemperatureUnit) async {
        state.temperatureUnit = unit
        try? await setTemperatureUnitUseCase.execute(unit)
    }


}

struct UnitsSelectorState {
    var volumeUnit: MeasureSystemVolumeUnit?
    var weightUnit: MeasureSystemWeightUnit?
    var temperatureUnit: MeasureSystemTemperatureUnit?
}

enum UnitsSelectorIntent {
    case loadData
    case setVolumeUnit(MeasureSystemVolumeUnit)
    case setWeightUnit(MeasureSystemWeightUnit)
    case setTemperatureUnit(MeasureSystemTemperatureUnit)
}
