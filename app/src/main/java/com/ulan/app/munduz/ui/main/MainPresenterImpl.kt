package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment
import javax.inject.Inject

class MainPresenterImpl : MainPresenter {

    private var view: MainView?

    @Inject
    constructor(mView: MainView) {
        this.view = mView
    }

    override fun addFragment(fragment: BaseFragment, title: String) {
        view?.showFragment(fragment, title)
    }

    override fun detachView() {
        view = null
    }
}