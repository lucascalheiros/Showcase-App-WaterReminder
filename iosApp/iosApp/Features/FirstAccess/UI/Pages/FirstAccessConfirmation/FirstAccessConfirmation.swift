//
//  FirstAccessConfirmation.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine
import Shared
import Factory

class FirstAccessConfirmationViewModel: ObservableObject {

    @Injected(\.setVolumeUnitUseCase)
    private var setVolumeUnitUseCase

    @Injected(\.getCalculatedIntakeUseCase)
    private var getCalculatedIntakeUseCase

    @Injected(\.getDailyWaterConsumptionUseCase)
    private var getDailyWaterConsumptionUseCase

    @Injected(\.setFirstDailyIntakeUseCase)
    private var setFirstDailyIntakeUseCase

    @Injected(\.saveDailyWaterConsumptionUseCase)
    private var saveDailyWaterConsumptionUseCase

    @Published private(set) var state: FirstAccessConfirmationState = .init()

    private var cancellableBag: Set<AnyCancellable> = []

    func send(_ intent: FirstAccessConfirmationIntent) {
        Task {
            switch intent {
            case .loadData:
                await loadData()
            case .setVolumeUnit(let volumeUnit):
                await setVolumeUnit(volumeUnit)
            case .setVolume(let volume):
                await setVolume(volume)
            }
        }
    }

    @MainActor
    private func loadData() async {
        try? await setFirstDailyIntakeUseCase.execute()
        getCalculatedIntakeUseCase.publisher()
            .first()
            .catch { _ in Empty() }
            .receive(on: RunLoop.main)
            .sink(receiveValue: { [weak self] volume in
                self?.state.expectedVolume = volume
                self?.state.volumeUnit = volume.volumeUnit()
            })
            .store(in: &cancellableBag)
        getDailyWaterConsumptionUseCase.publisher()
            .first()
            .catch { _ in Empty() }
            .receive(on: RunLoop.main)
            .sink(receiveValue: { [weak self] dailyIntake in
                if let volume = dailyIntake?.expectedVolume {
                    self?.state.selectedVolume = volume
                }
            })
            .store(in: &cancellableBag)
    }

    @MainActor
    private func setVolumeUnit(_ unit: MeasureSystemVolumeUnit) async {
        state.volumeUnit = unit
        try? await setVolumeUnitUseCase.execute(unit)
    }

    @MainActor
    private func setVolume(_ volume: MeasureSystemVolume) async {
        Logger.debug("Setting volume to \(volume.shortValueAndUnitFormatted)")
        state.selectedVolume = volume
        try? await saveDailyWaterConsumptionUseCase.save(volume)
    }


}

struct FirstAccessConfirmationState {
    var selectedVolume: MeasureSystemVolume = MeasureSystemVolumeCompanion().create(intrinsicValue: 0, measureSystemUnit_: MeasureSystemVolumeUnit.ml)
    var expectedVolume: MeasureSystemVolume = MeasureSystemVolumeCompanion().create(intrinsicValue: 0, measureSystemUnit_: MeasureSystemVolumeUnit.ml)
    var volumeUnit: MeasureSystemVolumeUnit = .ml
}

enum FirstAccessConfirmationIntent {
    case loadData
    case setVolumeUnit(MeasureSystemVolumeUnit)
    case setVolume(MeasureSystemVolume)
}
