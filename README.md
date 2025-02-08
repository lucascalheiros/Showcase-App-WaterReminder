# Welcome to WaterReminder

WaterReminder is water tracker mobile application for Android and iOS developed with the objective to be a showcase/learning application.

The application is built over Kotlin Multiplatform (KMP), so the domain logic for Android and iOS apps are unified, and organized through modular Clean Architecture, all the domain modules are exported through a single shared module exposed to iOS as a CocoaPod framework.

The Android UI is built over views/xml and the architecture for the UI Layer is Model View Presenter (MVP). The dependency injection framework is Koin.

The iOS UI is built over SwiftUI and the architecture for the UI Layer is Model View Intent (MVI). The dependency injection framework is Factory, used over the shared classes from KMP to provide a more friendly usage of the dependencies.

## Tech Stack

As mentioned before this is an KMP domain only shared application, so both Android and iOS UI are native.

### KMP (Domain)
* Koin
* Coroutines
* Flow
* Datastore preferences
* SQLDelight
* CleanArchirecture
* Feature based modularization, with domain and data modules for each feature
* Mockk

### Android (UI)
* Views/xml
* MVP
* Koin
* Coroutines
* Flow
* Jetpack Navigation
* Feature based modularization
* Mockk

### iOS (UI)
* SwiftUI
* MVI
* Combine
* SwiftConcurrency
* Factory (DI)
* Monolith

## Android Architecture

The Android architecture is based on MVP and Clean Architecture, in 3 main layers:

* UI Layer - Composed with several Feature modules that follow the MVP pattern: firstAccess, home, history, settings
* Domain Layer -  Composed with several Domain modules: remindNotifications, waterManagement, userInformation, measureSystem, firstAccess, home, history
* Data Layer -  Composed with several Data modules: notificationProvider, themeProvider, waterDataProvider, userProfileProviderm firstAccessDataProvider, measureSystemProvider, home

As usual for the Clean Architecture the Domain Layer have no access to the other 2 layers, and provide the Models, UseCases and Repository interfaces.

The data modules will access the domain defined repositories, and provide the implementation for those using dependency injection.

The feature modules from the UI layer will access the domain module usecase and model classes, to use the defined business logic.

For the UI layer it also follows the organization of Single Activity Application, so there is a single actvity on the app, and all the screens are made using Fragments.

The navigation of the application is made through Jetpack Navigation lib, and the navigation between different feature modules uses deeplinks.

For the async and reactive behavior, the app uses kotlin coroutine and flows.

The storage capabilities uses sqlDelight as databse and DataStore preferences for key-value operations, all the storage related operations are abstracted through the usage of repositories and contained only on the data layer.

### MVP Approach

The base structure for the MVP architecture is the BaseFragment and the BasePresenter.

The responsibility for BaseFragment is to provide the ViewContract to the presenter when the lifecycle of the fragment is started, and to dettach the view when the lifecycle of the fragment is stopped.

```kotlin
abstract class BaseFragment<Presenter, ViewContract>: Fragment() where Presenter : BasePresenter<ViewContract> {

    protected abstract val presenter: Presenter

    protected abstract val viewContract: ViewContract

    override fun onStart() {
        super.onStart()
        presenter.attachView(viewContract)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

}
```

The BasePresenter is extended over the android ViewModel to guarantee safe configuration changes when necessary, as well to be able to use a lifecycle aware coroutine scope for the async operations triggered by user interaction.
It contains a structure to provide a safe scope to consume flows that may need to call ViewContract methods. Each time the view is attached to the presenter it will launch a supervised job on the main thread to allow the overriden scopedViewUpdate() to collect or execute actions that may cause a ViewContract interaction.
When the view is dettached, the supervisor scope will cancel all of their children, since at that time it would be no longer safe execute view updates. This behavior is very similar to the lifecycleScope from Android's Activity/Fragment.

```kotlin
abstract class BasePresenter<ViewContract>(
    mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var viewReference: WeakReference<ViewContract>? = null

    private val supervisorJob = SupervisorJob()

    private val autoCancellableUIScope = CoroutineScope(mainDispatcher + supervisorJob)

    protected val view: ViewContract?
        get() = viewReference?.get()

    abstract fun CoroutineScope.scopedViewUpdate()

    fun attachView(view: ViewContract) {
        this.viewReference = WeakReference(view)
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            logError("Error thrown during UI update operation", exception)
        }
        autoCancellableUIScope.launch(exceptionHandler) {
            scopedViewUpdate()
        }
    }

    fun detachView() {
        supervisorJob.cancelChildren()
        viewReference = null
    }

}

```

An usual implementation of a presenter will look like: 

```kotlin
class SomePresenter(
    coroutineDispatcher: CoroutineDispatcher,
    state: SavedStateHandle,
    // ... Many usecases
) : BasePresenter<SomeContract.View>(coroutineDispatcher),
    SomeContract.Presenter {

    // ...
    private val dismissEvent = MutableStateFlow<Unit?>(null) // Events that must be handle are best mapped as states, this way if a background work should interact with UI, the state wont be lost

    override fun someWorkThatWillTriggerDismiss() {
        viewModelScope.launch {
            val success = someUseCase()
            if (success) {
                dismissEvent.value = Unit
            }
        }
    }

    override fun someRapidActionThatShowToast() {
        view?.showToast() // Events that occur due to user direct interaction could be handled directly
    } 

    override fun CoroutineScope.scopedViewUpdate() {
        collectDismissEvent()
        // ... Many other collects
    }

    private fun CoroutineScope.collectDismissEvent() = launch {
        dismissEvent.filterNotNull().collectLatest {
            view?.dismiss() // This view will never be null, since the Job will be active
        }
    }

}
```

## iOS Architecture

### MVI Swift Approad

MVI is great pattern for Android's Jetpack Compose, since SwiftUI is also a composable framework that behave similarly I thought on using it.
The approach I took for this project was a MVI without reducer, checkout this project for one that I used a MVI+Reducer in an Android project: https://github.com/lucascalheiros/TelegramFilterApp.
So for those who have experience with Android's MVI, let's trace some parallels on the technologies:

* State: data class -> struct, StateFlow -> @Published
* Intent: selaed interfaces -> enum
* Model: ViewModel (the android component) -> ObservableObject

And this is it, struct and @Published works greatly similarly as using a stateflow with data class, even cleaner! 

```kotlin

data class SomeUiState(
    val updated: Boolean = false
)

private val _uiState = MutableStateFlow(SomeUiState())
val uiState = _uiState.asStateFlow()

fun updateState() {
    _uiState.update {
        it.copy(updated = true)
    }
}
```

```swift
struct SomeUiState {
    var updated: Bool = false
}

@Published private(set) var uiState = SomeUiState()

fun updateState() {
    uiState.updated = false // Each time you change a struct property you are implicitly creating another struct, and the @Published will emit the new value
}
```

And swift enums are also great! Not as flexible as kotlin's sealed types in some ways, but they can hold parameters natively, which fits really well on what we need to transmit Intent's information.
The usual model for swift MVI will look like:

```swift
enum SomeIntent {
    case update(data: Bool)
}

class SomeViewModel: ObservableObject {

    @Injected(\.someUseCase)
    private var someUseCase

    @Published private(set) var uiState = SomeUiState()

    func send(_ intent: SomeIntent) {
        Task { @MainActor in // Execute the task work on the main thread if you update the state here
            switch intent {
            case update(data: let value):
                do {
                    defer {
                        uiState.updateActionLoading = false
                    }
                    uiState.updateActionLoading = true
                    try await someUseCase.execute(value)
                    uiState.updated = true
                } catch {
                    uiState.updatedError = true
                }
            //... cases
            }
        }
    }
}
```

And the view:

```swift
struct SomeView: View {

    @StateObject var someViewModel = SomeViewModel()

    // To simplify usage
    var state: SomeUiState {
        someViewModel.uiState
    }

    // To simplify usage
    var sendIntent: (SomeIntent) -> Void {
        someViewModel.send
    }

    var body: some View {
        if state.updated {
             Text("Updated")
        }  else {
           Button("Click to update", action: {
                sendIntent(.update(data: true))
            }) 
        }
    }
}
```




