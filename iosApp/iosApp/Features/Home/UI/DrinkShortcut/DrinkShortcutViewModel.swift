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
import Factory

class DrinkShortcutViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()

    @Injected(\.getDefaultVolumeShortcutsUseCase)
    private var getDefaultVolumeShortcutsUseCase

    @Injected(\.registerConsumedWaterUseCase)
    private var registerConsumedWaterUseCase

    @Published private(set) var state: DrinkShortcutState

    init(_ selectedDrink: WaterSourceType) {
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
