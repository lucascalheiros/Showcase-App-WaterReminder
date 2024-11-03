//
//  StringResourcesProtocol.swift
//  Core
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

public protocol StringResourcesProtocol: CaseIterable {
    var key: String { get }
    var table: String { get }
    var text: String { get }
    func format(_ arguments: any CVarArg...) -> String
}

public extension StringResourcesProtocol {
    var text: String {
        String(localized: String.LocalizationValue(key), table: table)
    }

    func format(_ arguments: any CVarArg...) -> String {
        String(format: text, arguments)
    }
}
