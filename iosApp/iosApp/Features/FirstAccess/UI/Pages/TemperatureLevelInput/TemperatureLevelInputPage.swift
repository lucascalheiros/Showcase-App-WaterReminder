//
//  TemperatureLevelInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared

struct TemperatureLevelInputPage: View {

    @StateObject var viewModel: TemperatureLevelInputViewModel = TemperatureLevelInputViewModel()

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
                            viewModel.send(.setTemperatureLevel(item))
                        }
                    }
                }
                .onChange(of: viewModel.state.temperatureLevel) { _, new in
                    withAnimation {
                        temperatureLevel = new
                    }
                }
                Picker("", selection: viewModel.state.temperatureScale.bindingWith {
                    viewModel.send(.setTemperatureScale($0))
                }) {
                    Text("C°").tag(TemperatureScale.celsius)
                    Text("F°").tag(TemperatureScale.fahrenheit)
                }
                .pickerStyle(.segmented)
                .padding()
                .onChange(of: viewModel.state.temperatureScale) { _, new in
                    withAnimation {
                        tempScale = new
                    }
                }

            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
        .onAppear {
            viewModel.send(.loadData)
        }
    }
}

extension TemperatureLevel {
    func bindingWith(_ onUpdate: @escaping (TemperatureLevel) -> Void) -> Binding<TemperatureLevel> {
        Binding(get: { self }, set: { onUpdate($0) })
    }
}

extension TemperatureScale {
    func bindingWith(_ onUpdate: @escaping (TemperatureScale) -> Void) -> Binding<TemperatureScale> {
        Binding(get: { self }, set: { onUpdate($0) })
    }
}


extension Double {
    func inUnit(_ unit: MeasureSystemTemperatureUnit) -> MeasureSystemTemperature {
        MeasureSystemTemperatureCompanion().create(intrinsicValue: self, measureSystemUnit: unit)
    }

    func inCelsius() -> MeasureSystemTemperature {
        inUnit(MeasureSystemTemperatureUnit.celsius)
    }
}
