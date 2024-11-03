//
//  ConfirmationScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared

struct FirstAccessConfirmationPage: View {

    @EnvironmentObject var theme: ThemeManager

    @StateObject var viewModel: FirstAccessConfirmationViewModel = FirstAccessConfirmationViewModel()

    @State var volumeInMl = 2000.0
    @State var volumeUnit = MeasureSystemVolumeUnit.ml
    @State var showVolumeInput = false

    var selectedVolumeFormatted: String {
        MeasureSystemVolumeCompanion()
            .create(intrinsicValue: volumeInMl, measureSystemUnit_: MeasureSystemVolumeUnit.ml)
            .toUnit(unit__: volumeUnit)
            .shortValueAndUnitFormatted
    }

    var calculatedVolumeFormatted: String {
        viewModel.state.expectedVolume.toUnit(unit__: volumeUnit).shortValueAndUnitFormatted
    }

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Spacer()
                Text("Your current expected water intake is:")
                Text(calculatedVolumeFormatted)
                    .underline()
                    .foregroundStyle(theme.current.primaryColor)
                    .onTapGesture {
                        viewModel.send(.setVolume(viewModel.state.expectedVolume))
                    }
                Text("Confirm your daily water intake objective")
                CardView {
                    Text("\(selectedVolumeFormatted)")
                        .padding()
                }
                .fixedSize()
                .onTapGesture {
                    showVolumeInput = true
                }
                .volumeInputAlert(
                    showAlert: $showVolumeInput,
                    volumeInput: Binding(
                        get: {
                            MeasureSystemVolumeCompanion()
                                .create(intrinsicValue: volumeInMl, measureSystemUnit_: MeasureSystemVolumeUnit.ml)
                                .toUnit(unit__: volumeUnit)
                                .intrinsicValue()
                        }, set: { newVolume in
                            if let newVolume {
                                let volume = MeasureSystemVolumeCompanion()
                                    .create(intrinsicValue: newVolume, measureSystemUnit_: volumeUnit)
                                viewModel.send(.setVolume(volume))
                            }
                        }
                    ), onCancel: {
                        showVolumeInput = false
                    }, onConfirm: { _ in
                        showVolumeInput = false

                    })
                .onChange(of: viewModel.state.selectedVolume.toUnit(unit__: MeasureSystemVolumeUnit.ml).intrinsicValue()) { _, new in
                    volumeInMl = new
                }
                Slider(value: volumeInMl.bindingWith {
                    viewModel.send(.setVolume( MeasureSystemVolumeCompanion()
                        .create(intrinsicValue: $0, measureSystemUnit_: MeasureSystemVolumeUnit.ml)))
                }, in: 1...5000)
                .padding()
                .tint(theme.current.primaryColor)
                Picker("", selection: volumeUnit.bindingWith {
                    volumeUnit = $0
                    viewModel.send(.setVolumeUnit($0))
                }) {
                    Text("ml").tag(MeasureSystemVolumeUnit.ml)
                    Text("OZ UK").tag(MeasureSystemVolumeUnit.ozUk)
                    Text("OZ US").tag(MeasureSystemVolumeUnit.ozUs)
                }
                .pickerStyle(.segmented)
                .padding()
                .onChange(of: viewModel.state.volumeUnit) { _, new in
                    withAnimation {
                        volumeUnit = new
                    }
                }
                Spacer()
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

extension MeasureSystemVolumeUnit {
    func bindingWith(_ onUpdate: @escaping (MeasureSystemVolumeUnit) -> Void) -> Binding<MeasureSystemVolumeUnit> {
        Binding(get: { self }, set: onUpdate)
    }
}

extension Double {
    func bindingWith(_ onUpdate: @escaping (Double) -> Void) -> Binding<Double> {
        Binding(get: { self }, set: onUpdate)
    }
}
