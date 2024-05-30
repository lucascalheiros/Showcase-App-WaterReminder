package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications

import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class ManageNotificationsPresenter(
    mainDispatcher: CoroutineDispatcher
): BasePresenter<ManageNotificationsContract.View>(mainDispatcher) {
    override fun CoroutineScope.scopedViewUpdate() {
        TODO("Not yet implemented")
    }
}