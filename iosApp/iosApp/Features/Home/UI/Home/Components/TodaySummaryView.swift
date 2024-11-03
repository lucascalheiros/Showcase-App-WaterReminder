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
        GeometryReader { geometry in
            CardView {
                if let summary = todaySummaryCard {
                    let hasLimitedSize = geometry.size.width > geometry.size.height
                    let lineWidth: CGFloat = hasLimitedSize ? 15 : 25
                    let chartPadding: CGFloat = hasLimitedSize ? 8 : 32
                    VStack {
                        HStack(spacing: 4) {
                            Spacer()
                                .layoutPriority(1)
                            Text(String(Int(summary.percentage)))
                                .font(theme.current.titleLarge)
                                .layoutPriority(2)
                            Text("%")
                                .font(theme.current.titleSmall)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .layoutPriority(1)
                        }
                        HStack(spacing: 4) {
                            Spacer()
                                .layoutPriority(1)
                            Text(summary.intake.shortValueFormatted)
                                .font(theme.current.titleSmall)
                                .layoutPriority(2)
                            Text(summary.intake.shortUnitFormatted)
                                .font(theme.current.titleSmall)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .layoutPriority(1)
                        }
                    }
                    ColoredCircleChart(
                        colorAndPercentage: summary.consumptionColorAndPercentage(colorScheme),
                        lineWidth: lineWidth
                    )
                    .aspectRatio(1.0, contentMode: .fit)
                    .padding(chartPadding)
                }
            }
        }
    }
}

extension DailyWaterConsumptionSummary {
    func consumptionColorAndPercentage(_ colorScheme: ColorScheme) -> [ColorAndPercentage] {
        consumptionPercentageByType.map {
            ColorAndPercentage(color: $0.waterSourceType.color(colorScheme), percentage: Double($0.percentage))
        }
    }
}
