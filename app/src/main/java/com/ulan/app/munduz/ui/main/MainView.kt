package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment

interface MainView {

    fun initBottomNav(id: Int)

    fun showFragment(fragment: BaseFragment, title: String)

}