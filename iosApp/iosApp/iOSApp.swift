import SwiftUI
import Shared
import Core

@main
struct iOSApp: App {

    init() {
        KoinInitHelperKt.doInitKoin()
        Logger.setLogger(SwiftyBeaverLogger())
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
