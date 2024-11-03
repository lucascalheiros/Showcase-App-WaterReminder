//
//  CustomAlertView.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CustomAlertView<Content: View, Buttons: View>: View {
    @EnvironmentObject var theme: ThemeManager
    var title: String
    let content: Content
    let buttons: Buttons
    @State private var isAnimating = false
    private let animationDuration = 0.5

    public init(
        title: String,
        @ViewBuilder content: () -> Content,
        @ViewBuilder buttons: () -> Buttons,
        isAnimating: Bool = false
    ) {
        self.title = title
        self.content = content()
        self.buttons = buttons()
    }

    public var body: some View {
        ZStack {
            Color.black
                .ignoresSafeArea()
                .opacity(0.4)
            if isAnimating {
                VStack(spacing: 0) {
                    Text(title)
                        .font(.headline)
                        .bold()
                        .foregroundStyle(theme.current.onDialogBackground)
                        .padding()
                    Divider()
                    content.layoutPriority(1)
                    Divider()
                    buttons
                        .fixedSize(horizontal: false, vertical: true)
                        .tint(theme.current.dialogActions)
                }
                .background(theme.current.dialogBackground)
                .frame(width: 300)
                .cornerRadius(20)
                .transition(.opacity)
                .padding()
            }
        }
        .ignoresSafeArea()
        .zIndex(.greatestFiniteMagnitude)
        .onAppear {
            show()
        }
    }

    func dismiss() {
        withAnimation(.easeInOut(duration: animationDuration)) {
            isAnimating = false
        }
    }

    func show() {
        withAnimation(.easeInOut(duration: animationDuration)) {
            isAnimating = true
        }
    }
}

public struct AlertButtonStyle: ButtonStyle {
    @EnvironmentObject var theme: ThemeManager

    public init() {}

    public func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .frame(minWidth: 0, maxWidth: .infinity)
            .contentShape(Rectangle())
            .padding()
            .background(configuration.isPressed ? theme.current.onDialogBackground.opacity(0.2) : .clear)
            .foregroundColor(theme.current.dialogActions)
            .opacity(configuration.isPressed ? 0.6 : 1.0)
    }
}
