//
//  HistoryScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Charts

public struct HistoryScreen: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var historyViewModel = HistoryViewModel()

    public init() {
    }

    var state: HistoryState {
        historyViewModel.state
    }

    var sendIntent: (HistoryIntent) -> Void {
        historyViewModel.send
    }

    public var body: some View {
        ScreenRootLayout {
            ScrollView {
                LazyVStack(spacing: 1) {
                    if let chartData = state.chartData {
                        Picker(selection: state.chartOption.bindingWith {
                            sendIntent(.onSelectChartOption($0))
                        }, label: Text(.chartPeriod)) {
                            Text(.chartOptionYear).tag(ChartOption_SV.year)
                            Text(.chartOptionMonth).tag(ChartOption_SV.month)
                            Text(.chartOptionWeek).tag(ChartOption_SV.week)
                        }
                        .pickerStyle(.segmented)
                        WaterConsumptionChart(
                            chartData: chartData,
                            onPreviousPeriod: {
                                sendIntent(.onPrevChartRange)
                            },
                            onNextPeriod: {
                                sendIntent(.onNextChartRange)
                            }
                        )
                    }
                    ForEach(state.summaries) { summary in
                        DayHistorySection(
                            summary: summary,
                            onDeleteConsumedWaterEntry: {
                                sendIntent(.onDeleteConsumptionClick($0))
                            }
                        )
                    }
                }
                .padding(16)
            }    
            .navigationTitle(HistorySR.historyTitle.text)
        }
    }
}

extension ChartOption_SV: BindableWithDismiss {

}
