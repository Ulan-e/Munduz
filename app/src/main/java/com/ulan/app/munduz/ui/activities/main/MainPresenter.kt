package com.ulan.app.munduz.ui.activities.main

import com.ulan.app.munduz.ui.base.BaseFragment

interface MainPresenter {

    fun addFragment(fragment: BaseFragment, title: String)

    fun detachView()

}