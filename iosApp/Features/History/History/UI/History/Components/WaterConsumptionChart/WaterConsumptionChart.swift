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
import DesignSystem

struct WaterConsumptionChart: View {
    var chartData: HistoryChartData

    var body: some View {
        CardView {
            VStack {
                switch chartData.chartPeriod.chartPeriodOption.swiftValue {
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
