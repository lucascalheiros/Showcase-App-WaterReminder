//
//  FirstAccessScreens+Utils.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

extension ScrollViewProxy {
    func animatedScrollTo(_ id: FirstAccessScreens, anchor: UnitPoint? = nil) {
        withAnimation {
            scrollTo(id, anchor: anchor)
        }
    }
}

extension View {
    func screenId(_ screen: FirstAccessScreens) -> some View {
        id(screen)
    }
}
