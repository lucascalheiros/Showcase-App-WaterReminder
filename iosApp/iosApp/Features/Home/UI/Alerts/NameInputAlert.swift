//
//  NameInputAlert.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension View {
    func nameInputAlert(
        showAlert: Binding<Bool>,
        nameInput: Binding<String>,
        onCancel: @escaping () -> Void,
        onConfirm: @escaping (String) -> Void
    ) -> some View {
        alert(String(localized: "Volume Input"), isPresented: showAlert) {
            TextField("", text: nameInput)
            Button("Cancel", action: onCancel)
            Button("Confirm", action: {
                let name = nameInput.wrappedValue
                if !name.isEmpty {
                    onConfirm(name)
                }
            }).disabled(nameInput.wrappedValue.isEmpty)
        }
    }
}
