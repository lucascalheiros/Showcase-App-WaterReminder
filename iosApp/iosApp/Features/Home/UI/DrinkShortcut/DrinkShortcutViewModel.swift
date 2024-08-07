//
//  DrinkShortcutViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine

class DrinkShortcutViewModel: ObservableObject {
    static func create(_ selectedDrink: WaterSourceType) -> DrinkShortcutViewModel {
        let injector = WaterManagementInjector()
        return DrinkShortcutViewModel(
            getDefaultVolumeShortcutsUseCase: injector.getDefaultVolumeShortcutsUseCase(),
            registerConsumedWaterUseCase: injector.registerConsumedWaterUseCase(),
            selectedDrink: selectedDrink
        )
    }
    private var cancellableBag = Set<AnyCancellable>()
    private let getDefaultVolumeShortcutsUseCase: GetDefaultVolumeShortcutsUseCase
    private let registerConsumedWaterUseCase: RegisterConsumedWaterUseCase

    @Published var state: DrinkShortcutState

    init(
        getDefaultVolumeShortcutsUseCase: GetDefaultVolumeShortcutsUseCase,
        registerConsumedWaterUseCase: RegisterConsumedWaterUseCase,
        selectedDrink: WaterSourceType
    ) {
        self.getDefaultVolumeShortcutsUseCase = getDefaultVolumeShortcutsUseCase
        self.registerConsumedWaterUseCase = registerConsumedWaterUseCase
        state = DrinkShortcutState(selectedWater: selectedDrink)
        observeStateProducer()
    }

    private func observeStateProducer() {

    }

    private func indexFromVolume(_ volume: MeasureSystemVolume) -> Int {
        switch volume.volumeUnit().swiftEnum {

        case .ml:
            Int(volume.intrinsicValue() / 10 ) - 1

        case .oz_uk, .oz_us:
            Int(volume.intrinsicValue() * 2 ) - 1
        }

    }

    func send(_ intent: DrinkShortcutIntent) {
        Task { @MainActor in
            switch intent {

            case .initData:
                let shortcuts = try await getDefaultVolumeShortcutsUseCase.get()
                state.defaultVolumeShortcuts = shortcuts
                let volumeUnit = shortcuts.medium.volumeUnit()
                let volumeUnitSw = shortcuts.medium.volumeUnit().swiftEnum
                state.volumeOptions = (1...500).map {
                    let intrinsicValue = switch volumeUnitSw {

                    case .ml:
                        Double($0) * 10.0

                    case .oz_uk, .oz_us:
                        Double($0) / 2.0
                    }
                    return MeasureSystemVolumeCompanion().create(intrinsicValue: intrinsicValue, measureSystemUnit_: volumeUnit)
                }
                state.selectedVolumeIndex = indexFromVolume(shortcuts.medium)
            case .onCancelClick:
                state.isDismissed = true

            case .onConfirmClick:
                try await registerConsumedWaterUseCase.register(
                    state.volumeOptions[state.selectedVolumeIndex],
                    state.selectedWater
                )
                state.isDismissed = true

            case .onSelectedVolume(let volume):
                state.selectedVolumeIndex = indexFromVolume(volume)
            }
        }
    }
}

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

enum DrinkShortcutIntent {
    case initData
    case onCancelClick
    case onConfirmClick
    case onSelectedVolume(MeasureSystemVolume)
}
