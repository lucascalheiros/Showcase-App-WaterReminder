package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class SettingsPresenter(
    mainDispatcher: CoroutineDispatcher,
) : BasePresenter<SettingsContract.View>(mainDispatcher),
    SettingsContract.Presenter {

    override fun CoroutineScope.scopedViewUpdate() {

    }
}