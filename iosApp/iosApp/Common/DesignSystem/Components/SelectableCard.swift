//
//  SelectableCard.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 02/10/24.
//

import SwiftUI


struct SelectableCard: View {
    @EnvironmentObject var theme: ThemeManager
    var title: String
    var description: String
    var isSelected: Bool

    var body: some View {
        CardView {
            VStack(alignment: .leading) {
                HStack {
                    Text(title)
                        .font(theme.current.titleSmall)
                    Spacer()
                    if isSelected {
                        ImageResources.checkMarkCircle.image()
                    }
                }
                Spacer()

                Text(description)
                    .multilineTextAlignment(.leading)
                    .font(theme.current.bodyLarge)

                Spacer()

            }
            .padding()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(isSelected ? theme.current.surfaceHighColor : theme.current.surfaceColor)

        }
        .overlay(RoundedRectangle(cornerRadius: 10)
            .stroke(isSelected ? theme.current.onBackgroundColor : theme.current.surfaceColor, lineWidth: 1))
    }
}
