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
        onClose: @escaping () -> Void
    ) -> some View {
        fullScreenCover(isPresented: showAlert) {
            HydrationFactorAlert(
                showAlert: showAlert, 
                hydration: hydration,
                onClose: onClose
            )
        }
        .transaction { transaction in
            transaction.disablesAnimations = true
        }
    }
}

private struct HydrationFactorAlert: View {
    @EnvironmentObject var theme: ThemeManager
    @Binding var showAlert: Bool
    @Binding var hydration: Float
    var onClose: () -> Void

    var body: some View {
        CustomAlertView(
            title: HomeSR.selectHydrationAlertTitle.text,
            content: {
                VStack {
                    Text($hydration.wrappedValue.formatted())
                    Slider(
                        value: $hydration,
                        in: (0.1)...(1.2),
                        step: 0.1
                    )
                }
                .padding()
                .frame(maxHeight: 100)
                .tint(theme.current.primaryColor)
            },
            buttons: {
                Button(action: onClose) {
                    Text(.alertClose)
                }
                .buttonStyle(AlertButtonStyle())
            }
        )
        .presentationBackground(.clear)
    }
}
