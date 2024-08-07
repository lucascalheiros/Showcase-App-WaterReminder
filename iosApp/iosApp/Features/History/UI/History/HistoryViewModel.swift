//
//  HistoryViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine

class HistoryViewModel: ObservableObject {

    static func create() -> HistoryViewModel {
        let injector = WaterManagementInjector()
        return HistoryViewModel(
            getDailyWaterConsumptionSummaryUseCase: injector.getDailyWaterConsumptionSummaryUseCase(),
            deleteConsumedWaterUseCase: injector.deleteConsumedWaterUseCase()
        )
    }

    private var cancellableBag = Set<AnyCancellable>()
    private let getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase
    private let deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase

    @Published var state = HistoryState()

    init(getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase, deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase) {
        self.getDailyWaterConsumptionSummaryUseCase = getDailyWaterConsumptionSummaryUseCase
        self.deleteConsumedWaterUseCase = deleteConsumedWaterUseCase
        observeStateProducer()
    }

    private func observeStateProducer() {
        getDailyWaterConsumptionSummaryUseCase.publisher
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] summaries in
                self?.state.summaries = summaries
            }).store(in: &cancellableBag)
    }

    func send(_ intent: HistoryIntent) {
        Task { @MainActor in
            switch intent {

            case .onDeleteConsumptionClick(let data):
                try await deleteConsumedWaterUseCase.delete(data.consumedWaterId)
            }
        }
    }

}

struct HistoryState {
    var summaries: [DailyWaterConsumptionSummary] = []
}

enum HistoryIntent {
    case onDeleteConsumptionClick(ConsumedWater)

}

enum HistorySections {
    case DayHeader
}
