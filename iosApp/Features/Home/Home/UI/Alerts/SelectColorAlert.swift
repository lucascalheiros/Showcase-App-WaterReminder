//
//  SelectDrinkAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem

extension View {
    func selectColorAlert(
        showAlert: Binding<Bool>,
        defaultColor: ThemedColor,
        onCancel: @escaping () -> Void,
        onSelect: @escaping (ThemedColor) -> Void
    ) -> some View {
        fullScreenCover(isPresented: showAlert) {
            SelectColorAlert(color: defaultColor, onCancel: onCancel, onConfirm: onSelect)
        }
        .transaction { transaction in
            transaction.disablesAnimations = true
        }
    }
}

struct SelectColorAlert: View {
    @EnvironmentObject var theme: ThemeManager
    @State var lightColor: Color = Color.black
    @State var darkColor: Color = Color.white
    var color: ThemedColor
    var onCancel: () -> Void
    var onConfirm: (ThemedColor) -> Void

    var body: some View {
        CustomAlertView(
            title: HomeSR.alertSelectColorTitle.text,
            content: {
                VStack(spacing: 0) {
                    ColorPicker(HomeSR.alertSelectColorOnDark.text, selection: $darkColor, supportsOpacity: false)
                        .padding()
                        .foregroundColor(darkColor)
                        .background(theme.current.surfaceColor)
                        .environment(\.colorScheme, .dark)

                    ColorPicker(HomeSR.alertSelectColorOnLight.text, selection: $lightColor, supportsOpacity: false)
                        .padding()
                        .foregroundColor(lightColor)
                        .background(theme.current.surfaceColor)
                        .environment(\.colorScheme, .light)
                }
            },
            buttons: {
                HStack(spacing: 0) {
                    Button(action: onCancel) {
                        Text(.alertCancel)
                    }
                    .buttonStyle(AlertButtonStyle())
                    SettingItemDivider()
                    Button(action: {
                        onConfirm(ThemedColor(lightColor: lightColor, darkColor: darkColor))
                    }) {
                        Text(.alertConfirm)
                    }
                    .buttonStyle(AlertButtonStyle())
                }
            }
        )
        .presentationBackground(.clear)
        .onAppear {
            darkColor = color.darkColor
            lightColor = color.lightColor
        }
    }
}
