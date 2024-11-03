//
//  SettingSectionHeaderTitle.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingSectionHeaderTitle: View {
    @EnvironmentObject var theme: ThemeManager
    let text: String

    init(_ text: String) {
        self.text = text
    }

    var body: some View {
        Text(text)
            .font(theme.current.titleSmall)
            .bold()
            .padding(.vertical, 16)
    }
}
