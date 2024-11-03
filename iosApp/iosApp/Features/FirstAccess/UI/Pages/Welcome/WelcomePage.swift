//
//  WelcomeScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI

struct WelcomePage: View {
    @EnvironmentObject var theme: ThemeManager

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Text(.welcomeTitle)
            }
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }
}
