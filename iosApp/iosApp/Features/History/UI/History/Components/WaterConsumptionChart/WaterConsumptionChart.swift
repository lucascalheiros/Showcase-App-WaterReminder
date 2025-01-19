//
//  WaterConsumptionChart.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Charts

struct WaterConsumptionChart: View {
    var chartData: HistoryChartData
    var onPreviousPeriod: () -> Void = { }
    var onNextPeriod: () -> Void = { }

    var body: some View {
        CardView {
            VStack {
                let chartType = chartData.chartPeriod.chartPeriodOption.swiftValue
                HStack {
                    Button(action: onPreviousPeriod) {
                        ImageResources.arrowLeft.image()
                    }
                    Text(chartData.chartPeriod.formattedText(chartType))
                        .frame(maxWidth: .infinity, alignment: .center)
                    Button(action: onNextPeriod) {
                        ImageResources.arrowRight.image()
                    }
                }.padding(.bottom, 8)
                switch chartType {
                case .week:
                    WeekChart(chartData: chartData)

                case .month:
                    MonthChart(chartData: chartData)

                case .year:
                    YearChart(chartData: chartData)
                }
            }
            .frame(height: 300)
            .padding(16)
        }
    }
}

private extension HistoryChartPeriod {

    func formattedText(_ type: ChartOption_SV) -> String {
        switch type {

        case .week:
            return "\(startDate.toDateSw().formatted(date: .numeric, time: .omitted)) - \(endDate.toDateSw().formatted(date: .numeric, time: .omitted))"
        case .month:
            let date = startDate.toDateSw()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "MMMM, yyyy"
            return dateFormatter.string(from: date)
        case .year:
            return String(describing: startDate.year)
        }

    }

}
