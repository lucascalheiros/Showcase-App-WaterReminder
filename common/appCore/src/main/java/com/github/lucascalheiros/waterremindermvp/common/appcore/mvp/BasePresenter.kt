package com.github.lucascalheiros.waterremindermvp.common.appcore.mvp

import androidx.lifecycle.ViewModel
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

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
