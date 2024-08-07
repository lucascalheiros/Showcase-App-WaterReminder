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
    func selectHydrationAlert(
        showAlert: Binding<Bool>,
        hydration: Binding<Float>,
        onCancel: @escaping () -> Void,
        onConfirm: @escaping (Float) -> Void
    ) -> some View {
        alert(String(localized: "Hydration Factor"), isPresented: showAlert) {
//            TODO
            Button("Cancel", action: onCancel)
            Button("Confirm", action: {
                onConfirm(hydration.wrappedValue)
            }).disabled(hydration.wrappedValue == 0)
        }
    }
}
