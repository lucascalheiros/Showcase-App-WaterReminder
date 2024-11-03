//
//  WaterReminderTests.swift
//  WaterReminderTests
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

@testable import WaterReminder_Showcase
import XCTest

final class WaterReminderTests: XCTestCase {

    func testCheckAllStringsHaveTranslation() throws {
        let resources: [any StringResourcesProtocol] = SettingsSR.allCases + NotificationsSR.allCases

        resources.forEach { res in
            XCTAssertNotEqual(res.key, res.text)
            XCTAssertNotEqual("", res.text)
        }
    }


    func testHelperSettingsStringCatalogEntries() throws {
        var generatedString = ""
        SettingsSR.allCases.forEach { res in
            if res.key == res.text {
                if !generatedString.isEmpty {
                    generatedString += ",\n"
                }
                generatedString += res.stringCatalogEmptyEntry
            }
        }
        XCTAssertEqual("", generatedString)
    }

    func testHelperNotificationsStringCatalogEntries() throws {
        var generatedString = ""
        NotificationsSR.allCases.forEach { res in
            if res.key == res.text {
                if !generatedString.isEmpty {
                    generatedString += ",\n"
                }
                generatedString += res.stringCatalogEmptyEntry
            }
        }
        XCTAssertEqual("", generatedString)
    }

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
