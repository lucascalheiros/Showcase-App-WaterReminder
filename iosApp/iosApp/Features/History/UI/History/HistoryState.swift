//
//  HistoryState.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared

struct HistoryState {
    var summaries: [DailyWaterConsumptionSummary] = []
    var chartData: HistoryChartData? = nil
    var baselineChartDate: Date = Date()
    var chartOption: ChartOption_SV = .week
}
