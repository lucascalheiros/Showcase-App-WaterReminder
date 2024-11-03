//
//  TodaySummaryView.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct TodaySummaryCard: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    let todaySummaryCard: DailyWaterConsumptionSummary?

    var body: some View {
        CardView {
            if let summary = todaySummaryCard {
                VStack {
                    Text(String(Int(summary.percentage)))
                        .font(theme.selectedTheme.titleLarge)
                    Text(String(summary.intake.shortValueAndUnitFormatted))
                        .font(theme.selectedTheme.titleSmall)
                }
                ColoredCircleChart(
                    colorAndPercentage: summary.consumptionPercentageByType.map {
                        ColorAndPercentage(color: $0.waterSourceType.color(colorScheme), percentage: Double($0.percentage))
                    }
                )
                .aspectRatio(1.0, contentMode: .fit)
                .padding(32)
            }
        }
    }
}
