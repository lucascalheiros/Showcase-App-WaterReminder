//
//  HistoryScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HistoryScreen: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var historyViewModel = HistoryViewModel.create()

    var body: some View {
        ScreenRootLayout {
            HStack {
                StyledText("History")
                Spacer()
            }.padding(.horizontal, 16)
            ScrollView {
                LazyVStack(spacing: 1) {
                    ForEach(historyViewModel.state.summaries) { summary in
                        Section(header: DayHeaderView(summary: summary)) {
                            VStack(spacing: 0) {
                                ForEach(Array(summary.consumedWaterList.enumerated()), id: \.element) { (index, data) in
                                    ConsumedWaterItemView(consumedWater: data)
                                        .animation(.interactiveSpring(), value: summary.consumedWaterList.count)
                                        .contextMenu(menuItems: {
                                            Button {
                                                historyViewModel.send(.onDeleteConsumptionClick(data))
                                            } label: {
                                                Text("Delete")
                                                ImageResources.deleteIcon.image()
                                            }
                                        })
                                    if index != summary.consumedWaterList.indices.last {
                                        SettingItemDivider()
                                    }
                                }
                            }
                            .background(theme.selectedTheme.surfaceColor)
                            .cornerRadius(12)

                        }
                    }
                }       
                .padding(.horizontal, 16)
            }
        }
    }
}

private struct DayHeaderView: View {
    @Environment(\.colorScheme) var colorScheme
    let summary: DailyWaterConsumptionSummary

    var body: some View {
        HStack {
            StyledText(summary.formattedDateForSectionHeader)
            Spacer()
            VStack(alignment: .trailing, spacing: 0) {
                StyledText(summary.formattedPercentage)
                StyledText(summary.intake.shortValueAndUnitFormatted)
            }
            ColoredCircleChart(
                colorAndPercentage: summary.consumptionPercentageByType.map {
                    ColorAndPercentage(color: $0.waterSourceType.color(colorScheme), percentage: Double($0.percentage))
                },
                lineWidth: 10
            )
            .frame(width: 80, height: 80)
        }
    }
}

private struct ConsumedWaterItemView: View {
    @Environment(\.colorScheme) var colorScheme
    let consumedWater: ConsumedWater

    var body: some View {
        HStack {
            Text(consumedWater.waterSourceType.name)
                .frame(minWidth: 80, alignment: .leading)
            Text(consumedWater.shortValueAndUnitFormattedWithHydration)
            Spacer()
            Text(consumedWater.formattedConsumptionTime)
        }
        .foregroundStyle(consumedWater.waterSourceType.color(colorScheme))
        .padding(.horizontal, 16)
        .frame(height: 48)
        .contentShape(Rectangle())
    }
}

extension ConsumedWater: Identifiable {
    public var id: Int64 {
        consumedWaterId
    }

    var formattedConsumptionTime: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .none
        dateFormatter.timeStyle = .short

        let date = Date(timeIntervalSince1970: Double(consumptionTime / 1000))
        return dateFormatter.string(from: date)
    }

    var shortValueAndUnitFormattedWithHydration: String {
        let volumeFormatted = volume.shortValueAndUnitFormatted
        return if waterSourceType.hydrationFactor != 1 {
            volumeFormatted + " (\(hydrationVolume.shortValueAndUnitFormatted))"
        } else {
            volumeFormatted
        }
    }
}

extension DailyWaterConsumptionSummary: Identifiable {
    public var id: Int32 {
        date.toEpochDays()
    }

    var formattedDateForSectionHeader: String {
        let timezone = TimeZone.current
        let currentTimestamp = Date().timeIntervalSince1970 + Double(timezone.secondsFromGMT())
        let todayEpochDay = Int32(currentTimestamp / (60 * 60 * 24))
        switch date.toEpochDays() {
        case todayEpochDay:
            return String(localized: "Today")
        case todayEpochDay - 1:
            return String(localized: "Yesterday")
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
}

