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
import Factory

class HistoryViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()
    private var chartDataCancellable: AnyCancellable?

    @Injected(\.getDailyWaterConsumptionSummaryUseCase)
    private var getDailyWaterConsumptionSummaryUseCase

    @Injected(\.deleteConsumedWaterUseCase)
    private var deleteConsumedWaterUseCase

    @Injected(\.getHistoryChartDataUseCase)
    private var getHistoryChartDataUseCase

    @Published private(set) var state = HistoryState()

    init() {
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
            case .onPrevChartRange:
                previousChartRange()
            case .onNextChartRange:
                nextChartRange()
            }
        }
    }

    private func previousChartRange() {
        let currentBaselineDate = state.baselineChartDate
        let newDate = switch state.chartOption {

        case .week:
            Calendar.current.date(byAdding: .weekOfYear, value: -1, to: currentBaselineDate)

        case .month:
            Calendar.current.date(byAdding: .month, value: -1, to: currentBaselineDate)

        case .year:
            Calendar.current.date(byAdding: .year, value: -1, to: currentBaselineDate)

        }
        state.baselineChartDate = newDate ?? Date()
        sinkChartData()
    }

    private func nextChartRange() {
        let currentBaselineDate = state.baselineChartDate
        let newDate = switch state.chartOption {

        case .week:
            Calendar.current.date(byAdding: .weekOfYear, value: 1, to: currentBaselineDate) ?? Date()

        case .month:
            Calendar.current.date(byAdding: .month, value: 1, to: currentBaselineDate) ?? Date()

        case .year:
            Calendar.current.date(byAdding: .year, value: 1, to: currentBaselineDate) ?? Date()

        }
        let currentDate = Date()
        if newDate < currentDate {
            state.baselineChartDate = newDate
        } else {
            state.baselineChartDate = currentDate
        }
        sinkChartData()
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
