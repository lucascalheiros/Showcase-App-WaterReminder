//
//  ColoredCircleChart.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct ColorAndPercentage {
    let color: Color
    let percentage: Double

    public init(color: Color, percentage: Double) {
        self.color = color
        self.percentage = percentage
    }
}

public struct ColoredCircleChart: View {
    var colorAndPercentage: [ColorAndPercentage]
    var lineWidth: CGFloat = 25

    public init(colorAndPercentage: [ColorAndPercentage], lineWidth: CGFloat = 25) {
        self.colorAndPercentage = colorAndPercentage
        self.lineWidth = lineWidth
    }

    private var accumulatedPercentage: [ColorAndPercentage] {
        var accumulated = 0.0
        return colorAndPercentage.map {
            accumulated += $0.percentage
            return ColorAndPercentage(color: $0.color, percentage: accumulated)
        }.reversed()
    }

    public var body: some View {
        GeometryReader { geometry in
            let halfSize = CGSize(width: geometry.size.width / 2, height: geometry.size.height / 2)
            let center = CGPoint(x: halfSize.width, y: halfSize.height)
            let radius = min(halfSize.width, halfSize.height)

            ZStack(alignment: .center) {
                Circle()
                    .stroke(Color.white.opacity(0.5), lineWidth: lineWidth)
                    .background(Circle().foregroundColor(.clear))

                ForEach(accumulatedPercentage, id: \.color) { data in
                    ArcView(
                        center: center,
                        radius: radius,
                        color: data.color,
                        lineWidth: lineWidth,
                        arcPercentage: data.percentage
                    )            
                }
            }
        }
        .padding()
    }

}

private struct ArcView: View {
    let center: CGPoint
    let radius: CGFloat
    let color: Color
    var lineWidth: CGFloat
    var arcPercentage: Double = 0.0
    @State private var animationProgress: Double = 0.0

    var body: some View {
        Arc(center: center, radius: radius, arcPercentage: arcPercentage * animationProgress)
            .stroke(color, style: StrokeStyle(lineWidth: lineWidth, lineCap: .round))
            .animation(.linear, value: arcPercentage)
            .onAppear {
                withAnimation(.linear) {
                    animationProgress = 1.0
                }
            }
    }

}

private struct Arc: Shape, Animatable  {
    let center: CGPoint
    let radius: CGFloat
    var arcPercentage: Double = 0.0
    var animatableData: Double {
        get { arcPercentage }
        set { arcPercentage = newValue }
    }

    func path(in rect: CGRect) -> Path {
        return Path { path in
            path.addArc(
                center: center,
                radius: radius,
                startAngle: .degrees(-90.0),
                endAngle: .degrees(arcPercentage * 360 - 90),
                clockwise: false
            )
        }
    }
}
