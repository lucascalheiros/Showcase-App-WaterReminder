//
//  ConsumedWaterItemView.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI
import Shared

struct ConsumedWaterItemView: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    var consumedWater: ConsumedWater

    var body: some View {
        HStack {
            Text(consumedWater.waterSourceType.name)
                .frame(minWidth: 80, alignment: .leading)
            Text(consumedWater.shortValueAndUnitFormattedWithHydration)
            Spacer()
            Text(consumedWater.formattedConsumptionTime)
        }
        .font(theme.current.bodyMedium)
        .foregroundStyle(consumedWater.waterSourceType.color(colorScheme))
        .padding(.horizontal, 16)
        .frame(height: 48)
        .contentShape(Rectangle())
    }
}

private extension ConsumedWater {

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
