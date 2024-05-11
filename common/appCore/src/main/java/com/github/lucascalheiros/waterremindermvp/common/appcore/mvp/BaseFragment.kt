package com.github.lucascalheiros.waterremindermvp.common.appcore.mvp

import androidx.fragment.app.Fragment

abstract class BaseFragment<Presenter, ViewContract>: Fragment() where Presenter : BasePresenter<ViewContract> {

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