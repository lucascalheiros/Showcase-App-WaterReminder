# Welcome to WaterReminder

WaterReminder is water tracker mobile application for Android and iOS developed with the objective to be a showcase/learning application.

The application is built over Kotlin Multiplatform (KMP), so the domain logic for Android and iOS apps are unified, and organized through modular Clean Architecture, all the domain modules are exported through a single shared module exposed to iOS as a CocoaPod framework.

The Android UI is built over views/xml and the architecture for the UI Layer is Model View Presenter (MVP). The dependency injection framework is Koin.

The iOS UI is built over SwiftUI and the architecture for the UI Layer is Model View Intent (MVI). The dependency injection framework is Factory, used over the shared classes from KMP to provide a more friendly usage of the dependencies.
