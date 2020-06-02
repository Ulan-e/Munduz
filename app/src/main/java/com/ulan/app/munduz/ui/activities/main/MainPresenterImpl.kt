package com.ulan.app.munduz.ui.activities.main

import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class MainPresenterImpl @Inject constructor() : BasePresenter<MainView>(), MainPresenter {

    override fun addFragment(fragment: BaseFragment, title: String) {
        getView()?.showFragment(fragment, title)
    }

}