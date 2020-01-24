package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment

interface MainView {

    fun initToolbar()
    fun initBottomNav()
    fun showFragment(fragment: BaseFragment, title: String)
}