package com.ulan.app.munduz.ui.main

import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.base.BaseView

interface MainView {
    fun initBottomNav()
    fun showFragment(fragment: BaseFragment, title: String)
}