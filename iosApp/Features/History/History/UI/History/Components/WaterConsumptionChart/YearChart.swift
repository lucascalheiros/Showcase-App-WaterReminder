//
//  YearChart.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI
import Shared
import Charts
import DesignSystem

struct YearChart: View {
    @Environment(\.colorScheme) var colorScheme
    var chartData: HistoryChartData

    var body: some View {
        Chart {
            ForEach(Array(chartData.consumptionVolume.enumerated()), id: \.element) { (index, volumeByPeriod) in
                if let volumeFromMonth = volumeByPeriod as? ConsumptionVolume.FromMonth {
                    if volumeFromMonth.consumptionVolumeByType.isEmpty {
                        BarMark(
                            x: .value(HistorySR.chartXDate.text, index),
                            y: .value(HistorySR.chartYVolume.text, 0)
                        )
                    }
                    ForEach(volumeFromMonth.consumptionVolumeByType) { consumption in
                        BarMark(
                            x: .value(HistorySR.chartXDate.text, index),
                            y: .value(HistorySR.chartYVolume.text, consumption.volume.intrinsicValue())
                        )
                        .foregroundStyle(consumption.waterSourceType.color(colorScheme))
                    }
                }
            }
        }
        .chartXAxis() {
            AxisMarks(preset: .aligned, position: .bottom, values: .automatic(desiredCount: 12)) { value in
                AxisValueLabel {
                    if let index = value.as(Int.self), let data = chartData.consumptionVolume[index] as? ConsumptionVolume.FromMonth {
                        Text(data.yearAndMonth.formattedForHistoryChartYearLabel)
                    }
                }
            }
        }
        .chartYAxis {
            AxisMarks(position: .leading)
        }
    }
}

private extension YearAndMonth {
    var formattedForHistoryChartYearLabel: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMMMM"
        let date = Calendar.current.date(from: DateComponents(month: Int(month)))!
        return dateFormatter.string(from: date)
    }
}

