# Welcome to WaterReminder

WaterReminder is water tracker mobile application for Android and iOS developed with the objective to be a showcase/learning application.

The application is built over Kotlin Multiplatform (KMP), so the domain logic for Android and iOS apps are unified, and organized through modular Clean Architecture, all the domain modules are exported through a single shared module exposed to iOS as a CocoaPod framework.

The Android UI is built over views/xml and the architecture for the UI Layer is Model View Presenter (MVP). The dependency injection framework is Koin.

The iOS UI is built over SwiftUI and the architecture for the UI Layer is Model View Intent (MVI). The dependency injection framework is Factory, used over the shared classes from KMP to provide a more friendly usage of the dependencies.

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

### MVP

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
When the view is dettached, the supervisor scope will cancell all of their children, since at that time it would be no longer safe execute view updates. This behavior is very similar to the lifecycleScope from Android's Activity/Fragment.

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
