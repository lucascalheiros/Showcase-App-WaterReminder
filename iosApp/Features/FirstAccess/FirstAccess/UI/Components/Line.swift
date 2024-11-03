//
//  Line.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 01/10/24.
//

import SwiftUI

struct Line: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.midX, y: 0))
        path.addLine(to: CGPoint(x: rect.midX, y: rect.height))
        return path
    }
}
