package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings

import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class SettingsPresenter(
    mainDispatcher: CoroutineDispatcher
) : BasePresenter<SettingsContract.View>(mainDispatcher),
    SettingsContract.Presenter {
    override fun CoroutineScope.scopedViewUpdate() {
        TODO("Not yet implemented")
    }
}