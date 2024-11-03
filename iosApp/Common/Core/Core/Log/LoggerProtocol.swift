//
//  LoggerProtocol.swift
//  Core
//
//  Created by Lucas Calheiros on 10/09/24.
//

import Foundation

public protocol LoggerProtocol {
    func custom(
        level: LogLevel,
        message: @autoclosure () -> Any,
        file: String,
        function: String ,
        line: Int,
        context: Any?
    )
}
