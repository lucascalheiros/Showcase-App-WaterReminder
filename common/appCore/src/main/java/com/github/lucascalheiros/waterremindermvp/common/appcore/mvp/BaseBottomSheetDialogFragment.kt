package com.github.lucascalheiros.waterremindermvp.common.appcore.mvp

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<Presenter, ViewContract>: BottomSheetDialogFragment() where Presenter : BasePresenter<ViewContract> {

    protected abstract val presenter: Presenter

    protected abstract val viewContract: ViewContract

    override fun onResume() {
        super.onResume()
        presenter.attachView(viewContract)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

}