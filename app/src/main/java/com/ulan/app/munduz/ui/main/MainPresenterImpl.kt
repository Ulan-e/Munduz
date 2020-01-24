package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment

class MainPresenterImpl(val mView: MainView) : MainPresenter {

    override fun addFragment(fragment: BaseFragment) {
        val title = fragment.toString()
        mView.showFragment(fragment, title)
    }
}