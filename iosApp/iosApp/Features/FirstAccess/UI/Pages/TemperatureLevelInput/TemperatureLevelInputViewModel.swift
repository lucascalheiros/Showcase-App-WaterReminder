//
//  TemperatureLevelInputViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Factory
import Combine

class TemperatureLevelInputViewModel: ObservableObject {

    @Injected(\.getUserProfileUseCase)
    private var getUserProfileUseCase

    @Injected(\.setUserProfileTemperatureLevelUseCase)
    private var setUserProfileTemperatureLevelUseCase

    @Injected(\.getTemperatureUnitUseCase)
    private var getTemperatureUnitUseCase

    @Injected(\.setTemperatureUnitUseCase)
    private var setTemperatureUnitUseCase

    @Published private(set) var state: TemperatureLevelState = .init()

    private var cancellableBag: Set<AnyCancellable> = []

    func send(_ intent: TemperatureLevelInputIntent) {
        Task {
            switch intent {
            case .loadData:
                loadData()
            case .setTemperatureLevel(let temperatureLevel):
                await setTemperatureLevel(temperatureLevel)
            case .setTemperatureScale(let temperatureScale):
                await setTemperatureScale(temperatureScale)
            }
        }
    }

    private func loadData() {
        getUserProfileUseCase.publisher()
            .first()
            .receive(on: RunLoop.main)
            .catch { _ in Empty() }
            .sink(receiveValue: { [weak self] profile in
                self?.state.temperatureLevel = TemperatureLevel.from(profile.temperatureLevel)
            })
            .store(in: &cancellableBag)
        getTemperatureUnitUseCase.execute()
            .first()
            .receive(on: RunLoop.main)
            .catch { _ in Empty() }
            .sink(receiveValue: { [weak self] unit in
                self?.state.temperatureScale = TemperatureScale.from(unit)
            })
            .store(in: &cancellableBag)
    }

    @MainActor
    private func setTemperatureLevel(_ temperatureLevel: TemperatureLevel) async {
        state.temperatureLevel = temperatureLevel
        try? await setUserProfileTemperatureLevelUseCase.set(temperatureLevel.level)
    }

    @MainActor
    private func setTemperatureScale(_ temperatureScale: TemperatureScale) async {
        state.temperatureScale = temperatureScale
        try? await setTemperatureUnitUseCase.execute(temperatureScale.unit)
    }
}

struct TemperatureLevelState {
    var temperatureLevel: TemperatureLevel = .moderate
    var temperatureScale: TemperatureScale = .celsius
}

enum TemperatureLevelInputIntent {
    case loadData
    case setTemperatureLevel(TemperatureLevel)
    case setTemperatureScale(TemperatureScale)
}
