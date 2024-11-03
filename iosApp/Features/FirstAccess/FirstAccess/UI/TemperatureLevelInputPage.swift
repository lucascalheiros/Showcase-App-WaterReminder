//
//  TemperatureLevelInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared

struct TemperatureLevelInputPage: View {
    @State var temperatureLevel = TemperatureLevel.moderate
    @State var tempScale = TemperatureScale.celsius

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Text("Hot conditions increase your water needs due to higher fluid loss. Cooler climates reduce this need.")
                LazyVGrid(columns: [GridItem(.flexible(minimum: 0, maximum: .infinity)), GridItem(.flexible(minimum: 0, maximum: .infinity))], spacing: 20) {
                    ForEach(TemperatureLevel.allCases) { item in
                        SelectableCard(
                            title: item.title,
                            description: item.description(tempScale),
                            isSelected: temperatureLevel == item
                        )
                        .animation(.default, value: tempScale)
                        .onTapGesture {
                            withAnimation {
                                temperatureLevel = item
                            }
                        }
                    }
                }
                Picker("", selection: $tempScale) {
                    Text("C°").tag(TemperatureScale.celsius)
                    Text("F°").tag(TemperatureScale.fahrenheit)
                }
                .pickerStyle(.segmented)
                .padding()

            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }
}


enum TemperatureLevel: CaseIterable, Identifiable {
    case cold
    case moderate
    case warm
    case hot

    var id: String {
        title
    }

    var title: String {
        switch self {

        case .cold:
            "Cold"
        case .moderate:
            "Moderate"
        case .warm:
            "Warm"
        case .hot:
            "Hot"
        }
    }

    func description(_ scale: TemperatureScale) -> String {
        let unit = switch scale {
        case .celsius:
            MeasureSystemTemperatureUnit.celsius
        case .fahrenheit:
            MeasureSystemTemperatureUnit.fahrenheit
        }

        return switch self {

        case .cold:
            String(
                format: "Less than %.0f°",
                20.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .moderate:
            String(
                format: "%.0f to %.0f°",
                20.0.inCelsius().toUnit(unit: unit).intrinsicValue(),
                25.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .warm:
            String(
                format: "%.0f to %.0f°",
                26.0.inCelsius().toUnit(unit: unit).intrinsicValue(),
                30.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .hot:
            String(
                format: "More than %.0f°",
                30.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        }
    }
}

enum TemperatureScale {
    case celsius
    case fahrenheit
}


extension Double {
    func inUnit(_ unit: MeasureSystemTemperatureUnit) -> MeasureSystemTemperature {
        MeasureSystemTemperatureCompanion().create(intrinsicValue: self, measureSystemUnit: unit)
    }

    func inCelsius() -> MeasureSystemTemperature {
        inUnit(MeasureSystemTemperatureUnit.celsius)
    }
}
