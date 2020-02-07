package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment
import javax.inject.Inject

class MainPresenterImpl : MainPresenter {

    private var mView: MainView?

    @Inject
    constructor(mView: MainView) {
        this.mView = mView
    }

    override fun addFragment(fragment: BaseFragment) {
        val title = fragment.toString()
        mView?.showFragment(fragment, title)
    }

    override fun detachView() {
        mView = null
    }
}