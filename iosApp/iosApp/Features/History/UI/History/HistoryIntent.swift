//
//  HistoryIntent.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared

enum HistoryIntent {
    case onSelectChartOption(ChartOption_SV)
    case onPrevChartRange(Date)
    case onNextChartRange(Date)
    case onDeleteConsumptionClick(ConsumedWater)
}
