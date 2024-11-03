//
//  FirstAccessViewModel.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 06/10/24.
//

import SwiftUI
import Factory

class FirstAccessViewModel: ObservableObject {

    @Injected(\.completeFirstAccessFlowUseCase)
    private var completeFirstAccessFlowUseCase

    func send(_ intent: FirstAccessIntent) {
        Task {
            switch intent {
            case .completeFirstAccessFlow:
                await completeFirstAccessFlow()
            }
        }
    }

    private func completeFirstAccessFlow() async {
        try? await completeFirstAccessFlowUseCase.execute()
    }
}

enum FirstAccessIntent {
    case completeFirstAccessFlow
}
