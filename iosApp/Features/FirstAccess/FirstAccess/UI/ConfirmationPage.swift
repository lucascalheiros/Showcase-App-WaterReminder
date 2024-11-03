//
//  ConfirmationScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared
import KMPShared
import DesignSystem

struct ConfirmationPage: View {
    @EnvironmentObject var theme: ThemeManager
    @State var volumeInMl = 2000.0
    @State var volumeScale = VolumeScale.ml
    @State var showVolumeInput = false

    var formattedVolume: String {
        MeasureSystemVolumeCompanion()
            .create(intrinsicValue: volumeInMl, measureSystemUnit_: MeasureSystemVolumeUnit.ml)
            .toUnit(unit__: volumeScale.unit)
            .shortValueAndUnitFormatted
    }

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Spacer()
                Text("Confirm your daily water intake objective")
                CardView {
                    Text("\(formattedVolume)")
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
                                .toUnit(unit__: volumeScale.unit)
                                .intrinsicValue()
                        }, set: { newVolume in
                            if let newVolume {
                                volumeInMl = MeasureSystemVolumeCompanion()
                                    .create(intrinsicValue: newVolume, measureSystemUnit_: volumeScale.unit)
                                    .toUnit(unit__: MeasureSystemVolumeUnit.ml)
                                    .intrinsicValue()
                            }
                        }
                    ), onCancel: {
                        showVolumeInput = false
                    }, onConfirm: { _ in
                        showVolumeInput = false

                    })
                Slider(value: $volumeInMl, in: 1...5000)
                    .padding()
                    .tint(theme.current.primaryColor)
                Picker("", selection: $volumeScale) {
                    Text("ml").tag(VolumeScale.ml)
                    Text("OZ UK").tag(VolumeScale.ozUK)
                    Text("OZ US").tag(VolumeScale.ozUS)
                }
                .pickerStyle(.segmented)
                .padding()
                Spacer()
            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }
}

enum VolumeScale {
    case ml
    case ozUK
    case ozUS

    var unit: MeasureSystemVolumeUnit {
        switch self {
        case .ml:
            MeasureSystemVolumeUnit.ml
        case .ozUK:
            MeasureSystemVolumeUnit.ozUk
        case .ozUS:
            MeasureSystemVolumeUnit.ozUs
        }
    }

}
