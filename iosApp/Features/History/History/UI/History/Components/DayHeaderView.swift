//
//  DayHeaderView.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI
import DesignSystem
import Shared

struct DayHeaderView: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    var summary: DailyWaterConsumptionSummary

    var body: some View {
        HStack {
            Text(summary.formattedDateForSectionHeader)
            Spacer()
            VStack(alignment: .trailing, spacing: 0) {
                Text(summary.formattedPercentage)
                Text(summary.intake.shortValueAndUnitFormatted)
            }
            ColoredCircleChart(
                colorAndPercentage: summary.consumptionPercentageByType.map {
                    ColorAndPercentage(color: $0.waterSourceType.color(colorScheme), percentage: Double($0.percentage))
                },
                lineWidth: 10
            )
            .frame(width: 80, height: 80)
        }
        .font(theme.current.bodyLarge)
    }
}

private extension DailyWaterConsumptionSummary {

    var formattedDateForSectionHeader: String {
        let timezone = TimeZone.current
        let currentTimestamp = Date().timeIntervalSince1970 + Double(timezone.secondsFromGMT())
        let todayEpochDay = Int32(currentTimestamp / (60 * 60 * 24))
        switch date.toEpochDays() {
        case todayEpochDay:
            return HistorySR.dayHeaderToday.text
        case todayEpochDay - 1:
            return HistorySR.dayHeaderYesterday.text
        default:
            let dateFormatter = DateFormatter()
            dateFormatter.dateStyle = .short
            dateFormatter.timeStyle = .none
            let dateSw = Date(timeIntervalSince1970: Double(date.atStartOfDay().epochSeconds))
            return dateFormatter.string(from: dateSw)
        }
    }

    var formattedPercentage: String {
        String(format: "%.0f%%", percentage)
    }

    var formattedDateForHistoryChart: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .short
        dateFormatter.timeStyle = .none
        let dateSw = Date(timeIntervalSince1970: Double(date.atStartOfDay().epochSeconds))
        return dateFormatter.string(from: dateSw)
    }
}
