//
//  SwiftyBeaverLogger.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Core
import SwiftyBeaver

struct SwiftyBeaverLogger: LoggerProtocol {
    let log = SwiftyBeaver.self

    init() {
        log.addDestination(ConsoleDestination())
    }

    func custom(
        level: Core.LogLevel,
        message: @autoclosure () -> Any,
        file: String = #file,
        function: String = #function,
        line: Int = #line,
        context: Any? = nil
    ) {
        log.custom(level: level.sbLevel, message: message(), file: file, function: function, line: line, context: context)
    }
}

extension Core.LogLevel {
    var sbLevel: SwiftyBeaver.Level {
        switch self {

        case .verbose:
            SwiftyBeaver.Level.verbose
        case .debug:
            SwiftyBeaver.Level.debug
        case .info:
            SwiftyBeaver.Level.info
        case .error:
            SwiftyBeaver.Level.error
        case .warning:
            SwiftyBeaver.Level.warning
        @unknown default:
            SwiftyBeaver.Level.debug

        }
    }
}
