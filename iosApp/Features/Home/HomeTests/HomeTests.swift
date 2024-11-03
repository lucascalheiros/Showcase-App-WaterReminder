//
//  HomeTests.swift
//  HomeTests
//
//  Created by Lucas Calheiros on 06/09/24.
//

import XCTest
@testable import Home
import Core

final class HomeTests: XCTestCase {

    func testHelperHomeStringCatalogEntries() throws {
        var generatedString = ""
        HomeSR.allCases.forEach { res in
            if res.key == res.text {
                if !generatedString.isEmpty {
                    generatedString += ",\n"
                }
                generatedString += res.stringCatalogEmptyEntry
            }
        }
        XCTAssertEqual("", generatedString)
    }
    
}

extension StringResourcesProtocol {
    var stringCatalogEmptyEntry: String  {
        """
            "\(key)" : {
            "extractionState" : "manual",
            "localizations" : {
                "en" : {
                    "stringUnit" : {
                        "state" : "translated",
                        "value" : ""
                    }
                }
            }
        }
        """
    }
}
