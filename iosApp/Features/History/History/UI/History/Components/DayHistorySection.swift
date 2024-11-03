//
//  DayHistorySection.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import Shared
import DesignSystem
import SwiftUI

struct DayHistorySection: View {
    @EnvironmentObject var theme: ThemeManager
    var summary: DailyWaterConsumptionSummary
    var onDeleteConsumedWaterEntry: (ConsumedWater) -> Void

    var body: some View {
        Section(header: DayHeaderView(summary: summary)) {
            VStack(spacing: 0) {
                ForEach(Array(summary.consumedWaterList.enumerated()), id: \.element) { (index, data) in
                    ConsumedWaterItemView(consumedWater: data)
                        .animation(.interactiveSpring(), value: summary.consumedWaterList.count)
                        .contextMenu(menuItems: {
                            Button {
                                onDeleteConsumedWaterEntry(data)
                            } label: {
                                Text(.deleteConsumedWater)
                                ImageResources.deleteIcon.image()
                            }
                        })
                    if index != summary.consumedWaterList.indices.last {
                        SettingItemDivider()
                    }
                }
            }
            .background(theme.current.surfaceColor)
            .cornerRadius(12)
        }
    }
}
