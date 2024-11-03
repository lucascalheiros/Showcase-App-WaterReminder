//
//  CustomAlertView.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomAlertView<Content: View, Buttons: View>: View {
    var title: String
    @ViewBuilder let content: Content
    @ViewBuilder let buttons: Buttons

    var body: some View {
        ZStack {
            Color.black
                .ignoresSafeArea()
                .opacity(0.3)
            VStack(spacing: 0) {
                Text(title)
                    .font(.headline)
                    .padding()
                Divider()
                content
                Divider()
                buttons

            }
            .background(Color(UIColor.systemGray5))
            .frame(width: 300)
            .cornerRadius(20)
            .padding()
        }
        .ignoresSafeArea()
        .zIndex(.greatestFiniteMagnitude)
    }
}

struct AlertButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .frame(minWidth: 0, maxWidth: .infinity)
            .padding()
            .background(Color(UIColor.systemGray5))
            .foregroundColor(.blue)
            .opacity(configuration.isPressed ? 0.6 : 1.0)
    }
}
