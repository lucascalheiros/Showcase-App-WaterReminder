//
//  Bool+BindingWithDismiss.swift
//  Util
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI

public extension Bool {
    func bindingWithDismiss(_ onDismiss: @escaping () -> Void) -> Binding<Bool> {
        Binding(
            get: { self },
            set: { newValue in
                if !newValue {
                    onDismiss()
                }
            }
        )
    }
}
