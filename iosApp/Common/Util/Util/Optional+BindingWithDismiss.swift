//
//  Optional+BindingWithDismiss.swift
//  Util
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI

public extension Optional {
    func bindingWith(
        _ onUpdate: ((Wrapped?) -> Void)? = nil
    ) -> Binding<Wrapped?> {
        Binding(
            get: { self },
            set: { newValue in
                onUpdate?(newValue)
            }
        )
    }

    func bindingWithDismiss(
        _ onUpdate: ((Wrapped) -> Void)? = nil,
        _ onDismiss: @escaping () -> Void
    ) -> Binding<Wrapped?> {
        Binding(
            get: { self },
            set: { newValue in
                if let newValue {
                    onUpdate?(newValue)
                } else {
                    onDismiss()
                }
            }
        )
    }
}
