//
//  HistorySR.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI
import Core

enum HistorySR: String, StringResourcesProtocol {

    case historyTitle
    case chartPeriod
    case chartOptionYear
    case chartOptionMonth
    case chartOptionWeek
    case chartXDate
    case chartYVolume
    case chartYExpectedVolume
    case dayHeaderToday
    case dayHeaderYesterday
    case deleteConsumedWater

    var key: String {
        rawValue
    }

    var table: String {
        "History"
    }

    var bundle: Bundle {
        Bundle(for: BundleClass.self)
    }
}

private class BundleClass {

}

extension Text {
    init(_ resource: HistorySR) {
        self.init(resource.text)
    }
}
