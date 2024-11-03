//
//  BindableWithDismiss.swift
//  Util
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI

public protocol BindableWithDismiss {

}

extension String: BindableWithDismiss {}
extension Float: BindableWithDismiss {}
extension Int: BindableWithDismiss {}

public extension BindableWithDismiss {
    func bindingWith(
        _ onUpdate: ((Self) -> Void)? = nil
    ) -> Binding<Self> {
        Binding(
            get: { self },
            set: { newValue in
                onUpdate?(newValue)
            }
        )
    }
}
