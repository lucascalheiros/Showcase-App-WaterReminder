//
//  HistoryIntent.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared

enum HistoryIntent {
    case onSelectChartOption(ChartOption_SV)
    case onPrevChartRange
    case onNextChartRange
    case onDeleteConsumptionClick(ConsumedWater)
}
