//
//  WeightInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared
import Combine

struct WeightInputPage: View {
    @EnvironmentObject var theme: ThemeManager

    @StateObject var viewModel: WeightInputViewModel = WeightInputViewModel()

    var state: WeightInputState { viewModel.state }

    var send: (WeightInputIntent) -> Void { viewModel.send }

    var body: some View {
        VStack(alignment: .center) {
            Text("Your body weight is key to determining your daily water needs.")
                .safeAreaPadding(.horizontal)
            weightPicker
            weightUnitSelector
        }
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
        .onAppear {
            send(.loadData)
        }
    }

    var weightPicker: some View {
        Picker("", selection: state.weight.bindingWith{
            send(.setWeight($0))
        }) {
            ForEach(0..<501) { index in
                Text(String(index))
                    .font(theme.current.titleSmall)
                    .tag(index)
            }
        }
        .pickerStyle(.wheel)
    }

    var weightUnitSelector: some View {
        Picker("", selection: state.weightOptions.bindingWith{
            send(.setWeightOptions($0))
        }) {
            Text(.weightKg).tag(WeightOptions.kg)
            Text(.weightLbs).tag(WeightOptions.lbs)
        }
        .pickerStyle(.segmented)
        .padding()
    }
}

struct WeightInputScreen_Preview: PreviewProvider {
    static var previews: some View {
        WeightInputPage().environmentObject(ThemeManager())
    }
}

private extension WeightOptions {
    func bindingWith(_ onUpdate: @escaping (WeightOptions) -> Void) -> Binding<WeightOptions> {
        Binding(
            get: { self },
            set: { newValue in
                onUpdate(newValue)
            }
        )
    }
}
