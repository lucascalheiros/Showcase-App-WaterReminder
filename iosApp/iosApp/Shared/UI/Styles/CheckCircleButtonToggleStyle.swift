//
//  RadionButtonToggleStyle.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import DesignSystem

public struct CheckCircleButtonToggleStyle: ToggleStyle {
    @EnvironmentObject var theme: ThemeManager
    @State private var isPressed = false

    public init() {
    }

    public func makeBody(configuration: Configuration) -> some View {
        return Button(action: {
            configuration.isOn.toggle()
        }, label: {
            configuration.isOn ? ImageResources.checkMarkCircle.image() : ImageResources.circle.image()
        })
        .opacity(isPressed ? 0.7 : 1.0)
        .gesture(
            DragGesture(minimumDistance: 0)
                .onChanged { _ in
                    withAnimation {
                        isPressed = true
                    }
                }
                .onEnded { _ in
                    withAnimation {
                        isPressed = false
                    }
                    configuration.isOn.toggle()
                }
        )

    }
}
