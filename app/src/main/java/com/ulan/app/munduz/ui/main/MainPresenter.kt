package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment

interface MainPresenter{

    fun addFragment(fragment: BaseFragment, title: String)
    fun detachView()

}