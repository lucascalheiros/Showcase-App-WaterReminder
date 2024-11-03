//
//  Logger.swift
//  Core
//
//  Created by Lucas Calheiros on 10/09/24.
//

public struct Logger {
    private static var logger: LoggerProtocol? = nil

    public static func setLogger(_ log: LoggerProtocol) {
        logger = log
    }

    public static func debug(_ message: @autoclosure () -> Any,
                             file: String = #file, function: String = #function, line: Int = #line, context: Any? = nil) {
        logger?.custom(level: .debug, message: message(), file: file, function: function, line: line, context: context)
    }

    public static func error(_ message: @autoclosure () -> Any,
                             file: String = #file, function: String = #function, line: Int = #line, context: Any? = nil) {
        logger?.custom(level: .error, message: message(), file: file, function: function, line: line, context: context)
    }

    public static func info(_ message: @autoclosure () -> Any,
                             file: String = #file, function: String = #function, line: Int = #line, context: Any? = nil) {
        logger?.custom(level: .info, message: message(), file: file, function: function, line: line, context: context)
    }

    public static func warning(_ message: @autoclosure () -> Any,
                               file: String = #file, function: String = #function, line: Int = #line, context: Any? = nil) {
        logger?.custom(level: .warning, message: message(), file: file, function: function, line: line, context: context)
    }

    public static func verbose(_ message: @autoclosure () -> Any,
                               file: String = #file, function: String = #function, line: Int = #line, context: Any? = nil) {
        logger?.custom(level: .verbose, message: message(), file: file, function: function, line: line, context: context)
    }
}
