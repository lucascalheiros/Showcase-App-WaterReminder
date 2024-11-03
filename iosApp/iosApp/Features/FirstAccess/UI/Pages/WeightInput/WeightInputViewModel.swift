//
//  WeightInputViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine
import Factory
import Shared

class WeightInputViewModel: ObservableObject {

    @Injected(\.getUserProfileUseCase)
    private var getUserProfileUseCase

    @Injected(\.setWeightUnitUseCase)
    private var setWeightUnitUseCase

    @Injected(\.setUserProfileWeightUseCase)
    private var setUserProfileWeightUseCase

    var cancellableBag = Set<AnyCancellable>()

    @Published private(set) var state = WeightInputState()

    func send(_ intent: WeightInputIntent) {
        Task { @MainActor in
            switch intent {

            case .loadData:
                loadData()
            case .setWeight(let weight):
                await setWeight(weight)
            case .setWeightOptions(let option):
                await setWeightOptions(option)
            }
        }
    }

    private func loadData() {
        getUserProfileUseCase.publisher()
            .first()
            .receive(on: RunLoop.main)
            .catch { _ in Empty<UserProfile, Never>() }
            .sink { [weak self] in
                self?.state.weight = Int($0.weight.intrinsicValue())
                self?.state.weightOptions = WeightOptions.from($0.weight.weightUnit())
            }
            .store(in: &cancellableBag)
    }

    @MainActor
    private func setWeight(_ weight: Int) async {
        state.weight = weight
        let weight_ = MeasureSystemWeightCompanion().create(intrinsicValue: Double(weight), measureSystemUnit_: state.weightOptions.unit)
        try? await setUserProfileWeightUseCase.set(weight_)
        loadData()
    }

    @MainActor
    private func setWeightOptions(_ weightOptions: WeightOptions) async {
        state.weightOptions = weightOptions
        try? await setWeightUnitUseCase.execute(weightOptions.unit)
        loadData()
    }

}

struct WeightInputState {
    var weight: Int = 70
    var weightOptions: WeightOptions = .kg
}

enum WeightInputIntent {
    case loadData
    case setWeight(Int)
    case setWeightOptions(WeightOptions)
}
