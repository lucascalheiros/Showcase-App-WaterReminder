//
//  ThemedColorView.swift
//  DesignSystem
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI

public struct ThemedColor {
    public let lightColor: Color
    public let darkColor: Color
    
    public init(lightColor: Color, darkColor: Color) {
        self.lightColor = lightColor
        self.darkColor = darkColor
    }
}

public struct ThemedColorView: View {
    @EnvironmentObject var theme: ThemeManager
    var themedColor: ThemedColor

    public init(themedColor: ThemedColor) {
        self.themedColor = themedColor
    }

    public var body: some View {
        ZStack {
            HalfCircle()
                .fill(themedColor.lightColor)
                .overlay(
                    HalfCircle().stroke(theme.current.onBackgroundColor, lineWidth: 1)
                )
                .aspectRatio(1.0, contentMode: .fill)

            HalfCircle()
                .fill(themedColor.darkColor)
                .overlay(
                    HalfCircle().stroke(theme.current.onBackgroundColor, lineWidth: 1)
                )
                .rotationEffect(.degrees(180))
                .aspectRatio(1.0, contentMode: .fill)
        }
        .aspectRatio(1.0, contentMode: .fill)
        .rotationEffect(.degrees(-45))
    }
}

struct HalfCircle: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.midX, y: rect.midY))
        path.addArc(
            center: CGPoint(x: rect.midX, y: rect.midY),
            radius: rect.width / 2,
            startAngle: .degrees(0),
            endAngle: .degrees(180),
            clockwise: false
        )
        return path
    }
}

struct ThemedColorView_Preview: PreviewProvider {

    static var previews: some View {
        ThemedColorView(themedColor: ThemedColor(lightColor: .black, darkColor: .black))
            .environmentObject(ThemeManager())
    }
}


