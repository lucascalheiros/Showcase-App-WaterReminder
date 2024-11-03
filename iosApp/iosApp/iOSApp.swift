import SwiftUI
import Shared

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
