//
//  WeekChart.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI
import Shared
import Charts
import DesignSystem

struct WeekChart: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    var chartData: HistoryChartData

    var body: some View {
        Chart {
            ForEach(Array(chartData.consumptionVolume.enumerated()), id: \.element) { (index, volumeByPeriod) in
                if let volume = chartData.volumeIntake {
                    LineMark(
                        x: .value(HistorySR.chartXDate.text, index),
                        y: .value(HistorySR.chartYExpectedVolume.text, volume.intrinsicValue())
                    )
                    .foregroundStyle(theme.current.primaryColor)
                }
                if let volumeFromDay = volumeByPeriod as? ConsumptionVolume.FromDay {
                    if volumeFromDay.consumptionVolumeByType.isEmpty {
                        BarMark(
                            x: .value(HistorySR.chartXDate.text, index),
                            y: .value(HistorySR.chartYVolume.text, 0)
                        )
                    }
                    ForEach(volumeFromDay.consumptionVolumeByType) { consumption in
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
            AxisMarks(preset: .aligned, position: .bottom, values: .automatic(desiredCount: 7)) { value in
                AxisValueLabel {
                    if let index = value.as(Int.self), let data = chartData.consumptionVolume[index] as? ConsumptionVolume.FromDay {
                        Text(data.date.formattedForHistoryChartWeekLabel)
                    }
                }
            }
        }
        .chartYAxis {
            AxisMarks(position: .leading)
        }
    }
}

private extension Kotlinx_datetimeLocalDate {
    var formattedForHistoryChartWeekLabel: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "EEEEE"
        let dateSw = toDateSw()
        return dateFormatter.string(from: dateSw)
    }
}
