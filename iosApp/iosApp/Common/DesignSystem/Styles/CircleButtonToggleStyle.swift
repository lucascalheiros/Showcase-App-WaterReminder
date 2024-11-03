//
//  CircleButtonToggleStyle.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CircleButtonToggleStyle: ToggleStyle {
    @EnvironmentObject var theme: ThemeManager
    @State private var isPressed = false

    public init() {
    }

    public func makeBody(configuration: Configuration) -> some View {
        return HStack {
            configuration.label
        }
        .frame(width: 36, height: 36)
        .overlay(
            Circle()
                .stroke(theme.current.onBackgroundColor, lineWidth: configuration.isOn ? 1 : 0)
        )
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
