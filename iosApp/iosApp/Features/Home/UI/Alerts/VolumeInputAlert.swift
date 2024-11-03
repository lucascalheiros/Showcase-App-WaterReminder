//
//  VolumeInputAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension View {
    func volumeInputAlert(
        showAlert: Binding<Bool>,
        volumeInput: Binding<Double?>,
        onCancel: @escaping () -> Void,
        onConfirm: @escaping (Double) -> Void
    ) -> some View {
        alert(String(localized: "Volume Input"), isPresented: showAlert) {
            TextField("", value: volumeInput, format: .number)
                .keyboardType(.decimalPad)
            Button("Cancel", action: onCancel)
            Button("Confirm", action: {
                if let volumeInput = volumeInput.wrappedValue {
                    onConfirm(volumeInput)
                }
            }).disabled(volumeInput.wrappedValue == nil)
        }
    }
}
