//
//  SettingItemDivider.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct SettingItemDivider: View {
    @EnvironmentObject var theme: ThemeManager

    public init() {}

    public var body: some View {
        Divider()
            .background(theme.current.onBackgroundColor)
    }
}
