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

    private var cancellableBag = Set<AnyCancellable>()
    private var chartDataCancellable: AnyCancellable?
    private let getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase
    private let deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase
    private let getHistoryChartDataUseCase: GetHistoryChartDataUseCase
    
    @Published private(set) var state = HistoryState()

    init(
        getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
        deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase,
        getHistoryChartDataUseCase: GetHistoryChartDataUseCase
    ) {
        self.getDailyWaterConsumptionSummaryUseCase = getDailyWaterConsumptionSummaryUseCase
        self.deleteConsumedWaterUseCase = deleteConsumedWaterUseCase
        self.getHistoryChartDataUseCase = getHistoryChartDataUseCase
        observeStateProducer()
    }

    private func observeStateProducer() {
        getDailyWaterConsumptionSummaryUseCase.publisher
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] summaries in
                self?.state.summaries = summaries
            }).store(in: &cancellableBag)
        sinkChartData()
    }

    func send(_ intent: HistoryIntent) {
        Task { @MainActor in
            switch intent {

            case .onDeleteConsumptionClick(let data):
                try? await deleteConsumedWaterUseCase.delete(data.consumedWaterId)
            case .onSelectChartOption(let option):
                state.chartOption = option
                sinkChartData()
            case .onPrevChartRange(let date):
                state.baselineChartDate = date
                sinkChartData()
            case .onNextChartRange(let date):
                state.baselineChartDate = date
                sinkChartData()
            }
        }
    }

    private func sinkChartData() {
        chartDataCancellable = getHistoryChartDataUseCase.publisher(option: state.chartOption, date: state.baselineChartDate)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] in
                self?.state.chartData = $0
            })
    }

}



struct ConsumedVolumeForDay {
    let date: Date
    let consumedVolume: [ConsumptionVolumeByType]
}

enum HistoryChartType {
    case week(week: [Date])
    case month(year: Int, month: Int)
    case year(year: Int)
}

enum Errors: Error {
    case noInstanceOfSelf
}

extension HistoryViewModel {
    convenience init() {
        let injector = SharedInjector()
        self.init(
            getDailyWaterConsumptionSummaryUseCase: injector.getDailyWaterConsumptionSummaryUseCase(),
            deleteConsumedWaterUseCase: injector.deleteConsumedWaterUseCase(),
            getHistoryChartDataUseCase: injector.getHistoryChartDataUseCase()
        )
    }
}
