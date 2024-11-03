//
//  CupCardView.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared
import SwiftUI

struct CupCardView: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    let card: CupCard

    var body: some View {
        CardView {
            VStack {
                switch card {
                case .item(let drink):
                    let color = drink.waterSourceType.color(colorScheme)
                    HStack {
                        Text(drink.waterSourceType.name)
                            .font(theme.current.titleSmall)
                            .foregroundStyle(color)
                        Spacer()
                    }
                    Spacer()
                    HStack {
                        Spacer()
                        Text(drink.volume.shortValueAndUnitFormatted)
                            .font(theme.current.titleMedium)
                            .foregroundStyle(color)
                    }
                case .addItem:
                    Text(.cupCardAdd)
                        .font(theme.current.titleMedium)
                }
            }.padding()
        }
        .cupCardFrame()
        .overlay(
            RoundedRectangle(cornerRadius: 10)
                .stroke(.gray, lineWidth: 1)
        )
    }
}

private extension CardView {
    func cupCardFrame() -> some View {
        let width = (UIScreen.screenWidth - 40) / 2
        return frame(
            minWidth: width,
            maxWidth: width,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .center
        )
    }
}
